package com.real.conetserver.tunnel.service;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.tunnel.Tunnel;
import com.real.conetserver.tunnel.factory.UserSessionFactory;
import com.real.conetserver.tunnel.model.UserSession;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author asuis
 */
@Service
public class UserSessionServiceImpl implements UserSessionService {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserSessionServiceImpl.class);

    private final RedisTemplate<String,Object> redisTemplate;

    private final UserSessionFactory userSessionFactory;


    @Autowired
    public UserSessionServiceImpl(RedisTemplate<String, Object> redisTemplate, UserSessionFactory userSessionFactory) {
        this.redisTemplate = redisTemplate;
        this.userSessionFactory = userSessionFactory;
    }

    @Override
    public void save(UserSession userSession) {
        try {
            redisTemplate.opsForHash().put("u_s","u_"+userSession.getUserInfo().getOpenId(),userSession);
        } catch (Exception e) {
            log.warn("userSession save fail:",e.getMessage());
        }
    }

    @Override
    public void bind(Tunnel tunnel, UserInfo userInfo) {
        try {
            UserSession userSession = get(userInfo.getOpenId());
            if (userSession!=null) {
                //清除已有信道
                Tunnel tunnel1 = userSession.getTunnel();
                redisTemplate.opsForHash().delete("tunnels","t_"+tunnel1.getTunnelId());

                //清除过时的userSession
                redisTemplate.opsForHash().delete("u_s",userInfo.getOpenId());

                //退出原来的房间
                Integer roomId = (Integer) redisTemplate.opsForHash().get("u_r",userSession.getUserInfo().getOpenId());

                redisTemplate.opsForList().remove("r_"+roomId,1,userSession);

                redisTemplate.opsForHash().delete("u_r",userSession.getUserInfo().getOpenId());
            }
            redisTemplate.opsForHash().put("tunnels","t_"+tunnel.getTunnelId(),userInfo);
        } catch (Exception e) {
            log.warn("bind tunnel userinfo fail",e.getMessage());
        }
    }

    @Override
    public UserSession get(Tunnel tunnel) {
        UserSession userSession = null;
        UserInfo userInfo = null;
        try {
            userInfo = (UserInfo) redisTemplate.opsForHash().get("tunnels","t_"+tunnel.getTunnelId());
        } catch (Exception e){
            log.warn("get userInfo by tunnel fail",e.getMessage());
        }
        userSession = userSessionFactory.create(userInfo,tunnel);
        return userSession;
    }

    @Override
    public UserSession get(String userId) {
        UserSession session = null;
        try {
            Object t = redisTemplate.opsForHash().get("u_s","u_"+userId);
            if (t!=null) {
                session = (UserSession) t;
            }
        } catch (Exception e) {
            log.warn("get usersession by user_id error");
        }
        return session;
    }

    @Override
    public boolean closeTunnel(Tunnel tunnel) {
        UserSession userSession = this.get(tunnel);
        try {
            redisTemplate.opsForHash().delete("u_s","u_"+userSession.getUserInfo().getOpenId(),1,userSession);
        } catch (Exception e) {
            log.warn("delete user-session error:",e.getMessage());
            return false;
        }
        try {
            redisTemplate.opsForHash().delete("tunnels","t_"+tunnel.getTunnelId());
        } catch (Exception e) {
            log.warn("tunnel-session delete error:",e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public void closeTunnelById(String tunnelId) {
        UserInfo userInfo = null;
        try {
            userInfo = (UserInfo) redisTemplate.opsForHash().get("tunnels","t_"+tunnelId);
        } catch (Exception e) {
            log.warn("get userinfo error:",e.getMessage());
        }
        if (userInfo!=null) {
            try {
                redisTemplate.opsForHash().delete("u_s","u_"+userInfo.getOpenId());
            } catch (Exception e) {
                log.warn("delete user-session error:",e.getMessage());
            }
        }
        try {
            redisTemplate.opsForHash().delete("tunnels","t_"+tunnelId);
        } catch (Exception e) {
            log.warn("delete tunnel bind userinfo error:",e.getMessage());
        }
    }
}
