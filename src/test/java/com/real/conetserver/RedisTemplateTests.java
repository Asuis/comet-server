package com.real.conetserver;

import com.qcloud.weapp.authorization.UserInfo;
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
    @Test
    public void test1() {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenId("1212");
        redisTemplate.opsForHash().put("hello",userInfo.getOpenId(),userInfo);
    }
}
