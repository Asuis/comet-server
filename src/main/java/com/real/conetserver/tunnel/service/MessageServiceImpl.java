package com.real.conetserver.tunnel.service;

import com.alibaba.fastjson.JSON;
import com.qcloud.weapp.tunnel.TunnelMessage;
import com.real.conetserver.constants.MessageType;
import com.real.conetserver.tunnel.model.Message;
import com.real.conetserver.tunnel.model.TunnelMessageContent;
import com.real.conetserver.tunnel.model.UserSession;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author asuis
 */
@Service
public class MessageServiceImpl implements MessageService {


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(MessageServiceImpl.class);
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Autowired
    public MessageServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendTunnelMessage(UserSession sender,TunnelMessage message) throws Exception {
        TunnelMessageContent messageContent = null;

        String senderId = null;
        try {
            JSONObject mess = (JSONObject)message.getContent();
            String receiverId = mess.getString("receiverId");
            String content = mess.getString("content");
            senderId = mess.getString("senderId");
            messageContent = new TunnelMessageContent();
            messageContent.setContent(content);
            messageContent.setReceiverId(receiverId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
         * 生成Message
         * */
        Message message1 = new Message();
        assert messageContent != null;
        message1.setSenderId(senderId);
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
        try {
            log.info("message:",message.getType());
            kafkaTemplate.send(message.getType(),json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}