package com.real.conetserver.tunnel;
import com.real.conetserver.tunnel.model.UserSession;
import com.real.conetserver.tunnel.service.RoomService;
import com.real.conetserver.tunnel.service.UserSessionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author asuis
 */
@RestController
@RequestMapping("/v1/room")
@Api("测试用接口创建房间")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    UserSessionService userSessionService;
    @RequestMapping("/create")
    public String create(@RequestParam String userId){
        UserSession userSession = userSessionService.get(userId);
        Long roomId = null;
        if (userSession != null) {
            roomId = roomService.makeRoomId(userSession);
        }
        return ""+roomId;
    }
}