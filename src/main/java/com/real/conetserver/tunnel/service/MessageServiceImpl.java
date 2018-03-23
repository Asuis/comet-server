package com.real.conetserver.tunnel.service;

import com.alibaba.fastjson.JSON;
import com.qcloud.weapp.tunnel.TunnelMessage;
import com.real.conetserver.constants.MessageType;
import com.real.conetserver.tunnel.model.Message;
import com.real.conetserver.tunnel.model.TunnelMessageContent;
import com.real.conetserver.tunnel.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author asuis
 */
@Service
@Slf4j
public class MessageServiceImpl implements MessageService {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public void sendTunnelMessage(UserSession sender,TunnelMessage message) throws Exception {
        TunnelMessageContent messageContent = null;
        try {
            messageContent = (TunnelMessageContent) message.getContent();
        } catch (Exception e) {
            log.warn("cast tunnel message error",e.getMessage());
        }
        assert messageContent != null;
        Message message1 = new Message();
        message1.setContent(messageContent.getContent());
        message1.setReceiverId(messageContent.getReceiverId());
        message1.setTime(new Date(System.currentTimeMillis()));
        MessageType type = MessageType.getType(message.getType());
        if (type!=null) {
            message1.setType(type);
        } else {
            throw new Exception("unknown message type");    
        }
        String json = null;
        try {
            json = JSON.toJSONString(message1);
        } catch (Exception e) {
            log.warn("tunnel message to json error",message);
        }
        kafkaTemplate.send(message.getType(),json);
    }
}