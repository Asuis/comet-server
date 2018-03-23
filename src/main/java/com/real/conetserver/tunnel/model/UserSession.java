package com.real.conetserver.tunnel.model;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.tunnel.Tunnel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author asuis
 */
public class UserSession implements Serializable {
    /**
     * 服务所在服务名
     * */
    private String serverId;
    /**
     * 用户信息
     */
    private UserInfo userInfo;
    /**
     * wafer2 信道服务
     * */
    private Tunnel tunnel;

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Tunnel getTunnel() {
        return tunnel;
    }

    public void setTunnel(Tunnel tunnel) {
        this.tunnel = tunnel;
    }
}