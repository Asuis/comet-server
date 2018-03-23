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
    @Autowired
    private RoomService roomService;
    @RequestMapping(value = "/push/{roomId}/{roomSize}",method = RequestMethod.POST)
    public String pushByRoomId(@PathVariable Long roomId,@PathVariable Integer roomSize,@RequestBody Message message){
        roomService.pushMessageByRoomId(roomId,roomSize,message);
        return "1";
    }
}