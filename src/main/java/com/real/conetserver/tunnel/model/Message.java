package com.real.conetserver.tunnel.model;

import com.real.conetserver.constants.MessageType;

import java.util.Date;

/**
 * @author asuis
 */
public class Message {
    /**
     * messageId 生成messageID 严格递增（每个房间）
     * */
    private Long messageId;
    /**
     * 发送人Id
     * */
    private String senderId;
    /**
     * 接收人Id
     * */
    private String receiverId;
    /**
     * 主要内容
     * */
    private String content;
    /**
     * 消息类型
     * */
    private MessageType type;
    /**
     * 消息时间
     * */
    private Date time;

    public Long getMessageId() {
        return this.messageId;
    }

    public String getSenderId() {
        return this.senderId;
    }

    public String getReceiverId() {
        return this.receiverId;
    }

    public String getContent() {
        return this.content;
    }

    public MessageType getType() {
        return this.type;
    }

    public Date getTime() {
        return this.time;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}