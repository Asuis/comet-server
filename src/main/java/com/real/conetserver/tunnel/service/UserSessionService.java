package com.real.conetserver.tunnel.service;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.tunnel.Tunnel;
import com.real.conetserver.tunnel.model.UserSession;

/**
 * @author asuis
 */
public interface UserSessionService {
    /**
     * 存储userSession
     * @param userSession
     */
    public void save(UserSession userSession);
    /**
     * 绑定信道和用户信息
     * */
    public void bind(Tunnel tunnel, UserInfo userInfo);

    public UserSession get(Tunnel tunnel);
    public UserSession get(String userId);
    /**
     * 删除tunnel有关的用户信息并且删除tunnel
     * */
    public boolean closeTunnel(Tunnel tunnel);
    
    public void closeTunnelById(String tunnelId);
}
