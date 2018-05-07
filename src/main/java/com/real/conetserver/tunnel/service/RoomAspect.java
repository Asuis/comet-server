package com.real.conetserver.tunnel.service;

import com.real.conetserver.constants.MessageType;
import com.real.conetserver.tunnel.model.Message;
import com.real.conetserver.tunnel.model.UserSession;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Date;

/**
 * @author asuis
 */
@Aspect
public class RoomAspect {

    private static final Logger log = LoggerFactory.getLogger(RoomAspect.class);

    private final RoomService roomService;

    private boolean isFirst = true;

    private Long roomId;

    private final RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public RoomAspect(RoomService roomService, RedisTemplate<String, Object> redisTemplate) {
        this.roomService = roomService;
        this.redisTemplate = redisTemplate;
    }
    /**
     * 为测试方便默认加入一号房间*/
    @After(value = "execution(* com.real.conetserver.tunnel.service.UserSessionService.save(..))&&args(userSession))")
    public void afterTunnelConnected(UserSession userSession){
        if (isFirst) {
            isFirst = false;
            roomId = roomService.makeRoomId(userSession);
        } else {
            roomService.joinRoom(roomId,userSession);
        }
        Message message = makeRoomMessage(userSession);
        roomService.pushMessageByRoomId(roomId,100,message);
    }
    private Message makeRoomMessage(UserSession session){
        Message message = new Message();
        message.setType(MessageType.ROOM);
        message.setTime(new Date(System.currentTimeMillis()));
        message.setReceiverId(roomId.toString());
        message.setContent(session.getUserInfo().getNickName()+"加入了房间!!!");
        message.setSenderId(session.getUserInfo().getOpenId());
        Long id = incrementHash("msg_gen","room_id",1L);
        message.setMessageId(id);
        return message;
    }
    private Long incrementHash(String key,String hashKey,Long delta) {
        try {
            if (null == delta) {
                delta=1L;
            }
            return redisTemplate.opsForHash().increment(key, hashKey, delta);
        } catch (Exception e) {
            log.warn("生成消息Id失败",e.getMessage());
        }
        return null;
    }
}