package com.real.conetserver.tunnel;

import com.real.conetserver.tunnel.model.Message;
import com.real.conetserver.tunnel.service.RoomService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author asuis
 */
@RestController
@Api("消息推送接口")
public class PushController {

    private final RoomService roomService;

    @Autowired
    public PushController(RoomService roomService) {
        this.roomService = roomService;
    }

    @RequestMapping(value = "/push/{roomId}",method = RequestMethod.POST)
    public String pushByRoomId(@PathVariable Long roomId,@RequestBody Message message){
        try {
            roomService.pushMessageByRoomId(roomId,200,message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }
}