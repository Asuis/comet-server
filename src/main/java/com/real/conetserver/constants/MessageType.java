package com.real.conetserver.constants;

/**
 * @author asuis
 */
public enum  MessageType {
    /**消息类型*/
    ROOM("room",1), BAR("bar",2), PRICE("price",3), CHAT("chat",4), QUIZ("quiz",5);

    private String name;
    private int index;

    MessageType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (MessageType c : MessageType.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }
    public static MessageType getType(String type){
        switch (type) {
            case "room":
                return ROOM;
            case "bar":
                return BAR;
            case "price":
                return PRICE;
            case "chat":
                return CHAT;
            case "quiz":
                return QUIZ;
            default:
                return null;
        }
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}
