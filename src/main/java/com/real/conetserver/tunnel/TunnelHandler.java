package com.real.conetserver.tunnel;

import com.qcloud.weapp.authorization.UserInfo;
import com.qcloud.weapp.tunnel.EmitError;
import com.qcloud.weapp.tunnel.Tunnel;
import com.qcloud.weapp.tunnel.TunnelMessage;
import com.real.conetserver.tunnel.model.UserSession;
import com.real.conetserver.tunnel.service.MessageService;
import com.real.conetserver.tunnel.service.RoomService;
import com.real.conetserver.tunnel.service.UserSessionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author asuis
 */
@Component
public class TunnelHandler implements com.qcloud.weapp.tunnel.TunnelHandler {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(TunnelHandler.class);
    private final UserSessionService userSessionService;
    private final MessageService messageService;
    private final RoomService roomService;

    @Autowired
    public TunnelHandler(UserSessionService userSessionService, MessageService messageService, RoomService roomService) {
        this.userSessionService = userSessionService;
        this.messageService = messageService;
        this.roomService = roomService;
    }

    /**
     * 当用户发起信道请求的时候调用，会得到用户信息，此时可以关联信道 ID 和用户信息
     * @param tunnel 发起连接请求的信道
     * @param userInfo 发起连接对应的用户（需要信道服务配置 checkLogin 为 true）
     * */
    @Override
    public void onTunnelRequest(Tunnel tunnel, UserInfo userInfo) {
        userSessionService.bind(tunnel,userInfo);
        log.info(String.format("Tunnel Connected: %s", tunnel.getTunnelId()));
    }
    /**
     * 当用户建立了信道连接之后调用，此时可以记录下已经连接的信道
     * @param tunnel 已经建立连接的信道，此时可以向信道发送消息
     * */
    @Override
    public void onTunnelConnect(Tunnel tunnel) {
        UserSession userSession = userSessionService.get(tunnel);
        if (userSession!=null) {
            userSessionService.save(userSession);
        } else {
            closeTunnel(tunnel);
        }
    }
    /**
     * 当信道收到消息时调用，此时可以处理消息，也可以向信道发送消息
     * @param tunnel 收到消息的信道
     * @param message 收到的消息
     * */
    @Override
    public void onTunnelMessage(Tunnel tunnel, TunnelMessage message) {
        UserSession userSession = userSessionService.get(tunnel);
        try {
            messageService.sendTunnelMessage(userSession,message);
        } catch (Exception e) {
            log.warn("send message kafka error:",e.getMessage());
        }
    }
    /**
     * 当信道关闭的时候调用，此时可以清理信道使用的资源
     * @param tunnel 已经关闭的信道
     * */
    @Override
    public void onTunnelClose(Tunnel tunnel) {
        userSessionService.closeTunnel(tunnel);
        roomService.exitRoom(tunnel);
        closeTunnel(tunnel);
    }
    private void closeTunnel(Tunnel tunnel){
        try {
            tunnel.close();
        } catch (EmitError emitError) {
            log.warn("emitError", emitError.getMessage());
        }
    }
}