package com.real.conetserver.tunnel;
import com.qcloud.weapp.ConfigurationException;
import com.qcloud.weapp.Hash;
import com.qcloud.weapp.authorization.LoginService;
import com.qcloud.weapp.authorization.LoginServiceException;
import com.qcloud.weapp.authorization.UserInfo;
import com.real.conetserver.constants.MessageType;
import com.real.conetserver.dto.Result;
import com.real.conetserver.dto.ResultCode;
import com.real.conetserver.tunnel.model.Message;
import com.real.conetserver.tunnel.model.RoomData;
import com.real.conetserver.tunnel.model.UserSession;
import com.real.conetserver.tunnel.service.RoomService;
import com.real.conetserver.tunnel.service.UserSessionService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;

/**
 * @author asuis
 */
@RestController
@RequestMapping("/v1/room")
@Api("测试用接口创建房间")
public class RoomController {
    private final RoomService roomService;
    private final UserSessionService userSessionService;

    @Autowired
    public RoomController(RoomService roomService, UserSessionService userSessionService) {
        this.roomService = roomService;
        this.userSessionService = userSessionService;
    }

    @RequestMapping("/create")
    public String create(@RequestParam String userId){
        UserSession userSession = userSessionService.get(userId);
        Long roomId = null;
        if (userSession != null) {
            roomId = roomService.makeRoomId(userSession);
        }
        return ""+roomId;
    }

    @RequestMapping("/join/m/{mid}")
    public Result<RoomData> joinRoom (HttpServletRequest request, HttpServletResponse response, @PathVariable("mid") Integer mid){
        LoginService loginService = new LoginService(request,response);
        UserInfo userInfo = null;
        try {
            userInfo = loginService.check();
        } catch (LoginServiceException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        Result<RoomData> result = new Result<>();

        assert userInfo != null;
        UserSession userSession = userSessionService.get(userInfo.getOpenId());
        if (userSession!=null) {
            if (roomService.isHaveRoomById(mid)) {
                roomService.joinRoom(Long.valueOf(mid),userSession);
            } else {
                roomService.openRoom(mid,userSession);
            }
            RoomData roomData = roomService.getRoomDataById(mid);
            result.setCode(ResultCode.SUCC);
            result.setData(roomData);
        } else {
            result.setCode(ResultCode.FAIL);
            result.setMsg("userSession not exist");
        }
        return result;
    }
}