package com.real.conetserver.constants;

/**
 * @author asuis
 */
public enum RoomType {
    /**
     * 房间类型
     */
    DEFAULT("default",1);

    private String name;
    private int index;

    RoomType(String name, int index) {
        this.name = name;
        this.index = index;
    }
}
