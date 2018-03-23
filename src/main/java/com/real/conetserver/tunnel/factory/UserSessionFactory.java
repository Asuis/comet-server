package com.real.conetserver.tunnel.factory;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.tunnel.Tunnel;
import com.real.conetserver.tunnel.model.UserSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author asuis
 */
@Component
public final class UserSessionFactory {

    @Value("${eureka.instance.appname}")
    public String serverId;
    public UserSession create(UserInfo userInfo, Tunnel tunnel) {
        UserSession userSession = new UserSession();
        userSession.setServerId(serverId);
        userSession.setTunnel(tunnel);
        userSession.setUserInfo(userInfo);
        return userSession;
    }
}
