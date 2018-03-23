package com.real.conetserver.tunnel.service;

import com.qcloud.weapp.tunnel.TunnelMessage;
import com.real.conetserver.tunnel.model.UserSession;

/**
 * @author asuis
 */
public interface MessageService {
    /**
     * 发送信道消息
     * @param message 信道消息
     */
    public void sendTunnelMessage(UserSession sender, TunnelMessage message) throws Exception;
}
