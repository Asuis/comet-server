package com.real.conetserver.tunnel.model;

import com.real.conetserver.constants.RoomType;
import lombok.Data;

import java.util.ArrayList;

/**
 * @author asuis
 */
@Data
public class Room {
    private Long roomId;
    private RoomType roomType;
    private int roomSize;
    private ArrayList<UserSession> roomMembers;
    public Room(Long roomId, RoomType roomType,Integer roomSize) {
        this.roomId = roomId;
        this.roomType = roomType;
        this.roomSize = roomSize;
        this.roomMembers = new ArrayList<>(roomSize);
    }
    public void joinRoom(UserSession userSession) {
        roomMembers.add(userSession);
    }
}