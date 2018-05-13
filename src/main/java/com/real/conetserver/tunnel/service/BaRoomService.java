package com.real.conetserver.tunnel.service;

/**
 * @author asuis
 */
public interface BaRoomService {
    void joinRoom();
    void exitRoom();
    void pushRoomById(Integer roomId);
}
