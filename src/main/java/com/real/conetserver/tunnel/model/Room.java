package com.real.conetserver.tunnel.model;

import com.real.conetserver.constants.RoomType;

import java.util.ArrayList;

/**
 * @author asuis
 */
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

    public Long getRoomId() {
        return this.roomId;
    }

    public RoomType getRoomType() {
        return this.roomType;
    }

    public int getRoomSize() {
        return this.roomSize;
    }

    public ArrayList<UserSession> getRoomMembers() {
        return this.roomMembers;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public void setRoomMembers(ArrayList<UserSession> roomMembers) {
        this.roomMembers = roomMembers;
    }

    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof Room)) return false;
        final Room other = (Room) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$roomId = this.getRoomId();
        final Object other$roomId = other.getRoomId();
        if (this$roomId == null ? other$roomId != null : !this$roomId.equals(other$roomId)) return false;
        final Object this$roomType = this.getRoomType();
        final Object other$roomType = other.getRoomType();
        if (this$roomType == null ? other$roomType != null : !this$roomType.equals(other$roomType)) return false;
        if (this.getRoomSize() != other.getRoomSize()) return false;
        final Object this$roomMembers = this.getRoomMembers();
        final Object other$roomMembers = other.getRoomMembers();
        if (this$roomMembers == null ? other$roomMembers != null : !this$roomMembers.equals(other$roomMembers))
            return false;
        return true;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $roomId = this.getRoomId();
        result = result * PRIME + ($roomId == null ? 43 : $roomId.hashCode());
        final Object $roomType = this.getRoomType();
        result = result * PRIME + ($roomType == null ? 43 : $roomType.hashCode());
        result = result * PRIME + this.getRoomSize();
        final Object $roomMembers = this.getRoomMembers();
        result = result * PRIME + ($roomMembers == null ? 43 : $roomMembers.hashCode());
        return result;
    }

    protected boolean canEqual(Object other) {
        return other instanceof Room;
    }

    public String toString() {
        return "Room(roomId=" + this.getRoomId() + ", roomType=" + this.getRoomType() + ", roomSize=" + this.getRoomSize() + ", roomMembers=" + this.getRoomMembers() + ")";
    }
}