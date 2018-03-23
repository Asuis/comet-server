package com.real.conetserver.tunnel.model;

import com.real.conetserver.constants.MessageType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author asuis
 */
@Getter
@Setter
public class Message {
    private Long messageId;
    private String senderId;
    private String receiverId;
    private String content;
    private MessageType type;
    private Date time;
}