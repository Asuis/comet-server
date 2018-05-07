package com.real.conetserver;

import com.qcloud.weapp.authorization.UserInfo;
import com.real.conetserver.tunnel.model.UserSession;
import com.real.conetserver.tunnel.service.RoomService;
import com.real.conetserver.tunnel.service.UserSessionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTemplateTests {
    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserSessionService userSessionService;

    @Test
    public void test1() {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId("1212");
        redisTemplate.opsForHash().put("hello",userInfo.getOpenId(),userInfo);
    }
    @Test
    public void exitRoom() {
        UserSession userSession = userSessionService.get("oAgY65Dqtd-8Zg4GoQ_7HCZ6cYUA");
        roomService.exitRoom(userSession);
    }
}
