package com.real.conetserver.tunnel.service;
import com.qcloud.weapp.tunnel.EmitError;
import com.qcloud.weapp.tunnel.EmitResult;
import com.qcloud.weapp.tunnel.Tunnel;
import com.qcloud.weapp.tunnel.TunnelInvalidInfo;
import com.real.conetserver.constants.RoomType;
import com.real.conetserver.tunnel.model.Message;
import com.real.conetserver.tunnel.model.Room;
import com.real.conetserver.tunnel.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author asuis
 */
@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private UserSessionService userSessionService;

    @Override
    public Long makeRoomId(UserSession userSession) {
        Long id = incrementHash("room_gen","room_id",1L);
        try {
            ListOperations<String,Object> re = redisTemplate.opsForList();
            re.rightPush("r_"+id.toString(),userSession);
        } catch (Exception e) {
            log.warn("redis error",e.getMessage());
        }
        bindRoomAndUser(id,userSession);
        return id;
    }

    @Override
    public Room getRoomById(Long roomId,Integer roomSize) {
        List<Object> userSessions = null;
        try {
            userSessions = redisTemplate.opsForList().range("r_"+roomId,0L,roomSize);
        } catch (Exception e){
            log.warn("error getRoom:",e.getMessage());
        }
        if (userSessions!=null) {
            Room room = new Room(roomId,RoomType.DEFAULT,roomSize);
            try {

                for (Object session:userSessions) {
                    UserSession userSession = (UserSession) session;
                    room.joinRoom(userSession);
                }

            } catch (Exception e) {
                log.warn("cast error object to user session:",e.getMessage());
            }
            return room;
        }
        return null;
    }

    @Override
    public void joinRoom(Long roomId,UserSession userSession) {
        try {
            redisTemplate.opsForList().rightPushIfPresent("r_"+roomId,userSession);
        } catch (Exception e) {
            log.warn("error 添加失败",e.getMessage());
        }
        bindRoomAndUser(roomId,userSession);
    }

    @Override
    public void exitRoom(UserSession userSession,Long roomId) {
        try {
            redisTemplate.opsForList().remove("r_"+roomId,1,userSession);
        } catch (Exception e) {
            log.warn("error remove fail",e.getMessage());
        }
    }

    @Override
    public void exitRoom(UserSession userSession) {
        Long roomId = null;
        try {
            roomId = (Long) redisTemplate.opsForHash().get("u_r",userSession.getUserInfo().getOpenId());
        } catch (Exception e) {
            log.warn("get bind room session error:",e.getMessage());
        }
        if (roomId!=null) {
            clearBindRoomAndUser(userSession);
            exitRoom(userSession,roomId);
        }
    }

    @Override
    public void exitRoom(Tunnel tunnel) {
        UserSession userSession = userSessionService.get(tunnel);
        exitRoom(userSession);
    }

    @Override
    public void removeRoomById(Long roomId) {
        try {
            redisTemplate.delete("r_"+roomId);
        } catch (Exception e) {
            log.warn("房间 删除失败");
        }
    }

    @Override
    public void pushMessageByRoomId(Long roomId,Integer roomSize ,Message message) {
       Room room = getRoomById(roomId,roomSize);
       try {
           ArrayList<UserSession> userSessions = room.getRoomMembers();
           for (UserSession u : userSessions) {
               Tunnel tunnel = u.getTunnel();
               EmitResult result = null;
               try {
                   result = tunnel.emit(message.getType().getName(),message);   
               } catch (EmitError e) {
                   log.warn("userSession-"+u.getUserInfo().getNickName()+"推送失败",e.getMessage());
                   userSessionService.closeTunnel(tunnel);
               }
               ArrayList<TunnelInvalidInfo> list = result.getTunnelInvalidInfos();
               for (TunnelInvalidInfo t:list) {
                   userSessionService.closeTunnelById(t.getTunnelId());
               }
           }
       } catch (Exception e) {
           log.warn("user-session push messages error:",e.getMessage());
       }
    }

    private void bindRoomAndUser(Long roomId,UserSession userSession){
        try {
            redisTemplate.opsForHash().put("u_r",userSession.getUserInfo().getOpenId(),roomId);
        } catch (Exception e) {
            log.warn("bind user and room error",e.getMessage());
        }
    }
    private void clearBindRoomAndUser(UserSession userSession) {
        try {
            redisTemplate.opsForHash().delete("u_r",userSession.getUserInfo().getOpenId());
        } catch (Exception e) {
            log.warn("clear bind user and room error:",e.getMessage());
        }
    }

    /**
     * 获取唯一Id
     * @param key redis键名
     * @param hashKey 保存hash表的键
     * @param delta 增加量（不传采用1）
     * @return 唯一id
     */
    private Long incrementHash(String key,String hashKey,Long delta) {
        try {
            if (null == delta) {
                delta=1L;
            }
            return redisTemplate.opsForHash().increment(key, hashKey, delta);
        } catch (Exception e) {//redis宕机时采用uuid的方式生成唯一id
            int first = new Random(10).nextInt(8) + 1;
            int randNo= UUID.randomUUID().toString().hashCode();
            if (randNo < 0) {
                randNo=-randNo;
            }
            return Long.valueOf(first + String.format("%16d", randNo));
        }
    }
}