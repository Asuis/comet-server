package com.real.conetserver.tunnel.service;

import com.qcloud.weapp.tunnel.Tunnel;
import com.real.conetserver.tunnel.model.Message;
import com.real.conetserver.tunnel.model.Room;
import com.real.conetserver.tunnel.model.UserSession;

import java.util.List;

/**
 * @author asuis
 */
public interface RoomService {
    /**
     * @return roomId 生成房间
     */
    public Long makeRoomId(UserSession userSession);

    /**
     * 查询房间
     * @param roomId 房间Id
     * @param roomSize 房间大小
     * @return 房间
     */
    public Room getRoomById(Long roomId,Integer roomSize);

    /**
     * 加入房间
     * @param userSession 用户会话
     * @param roomId 房间号
     */
    public void joinRoom(Long roomId,UserSession userSession);

    /**
     * 退出房间
     * @param userSession 用户会话
     * @param roomId 房间号
     */
    public void exitRoom(UserSession userSession,Long roomId);
    public void exitRoom(UserSession userSession);
    public void exitRoom(Tunnel tunnel);
    /**
     * 从缓存中删除房间，同时使用mysql持久化
     * @param roomId 房间id
     */
    public void removeRoomById(Long roomId);

    /**
     * 推送消息
     * @param roomId 房间id
     * @param roomSize 房间大小
     * @param message 要发送的信息
     */
    public void pushMessageByRoomId(Long roomId,Integer roomSize,Message message);
}
