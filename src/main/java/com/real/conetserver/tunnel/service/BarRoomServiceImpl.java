package com.real.conetserver.tunnel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;
@Service
public class BarRoomServiceImpl implements BaRoomService {

    @Autowired
    private final RedisTemplate<String,Object> redisTemplate;

    public BarRoomServiceImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取唯一Id
     * @param key redis键名
     * @param hashKey 保存hash表的键
     * @param delta 增加量（不传采用1）
     * @return 唯一id
     */
    private Long incrementHash(String key,String hashKey,Long delta) {
        try {
            if (null == delta) {
                delta=1L;
            }
            return redisTemplate.opsForHash().increment(key, hashKey, delta);
        } catch (Exception e) {//redis宕机时采用uuid的方式生成唯一id
            int first = new Random(10).nextInt(8) + 1;
            int randNo= UUID.randomUUID().toString().hashCode();
            if (randNo < 0) {
                randNo=-randNo;
            }
            return Long.valueOf(first + String.format("%16d", randNo));
        }
    }


    @Override
    public void joinRoom() {

    }

    @Override
    public void exitRoom() {

    }

    @Override
    public void pushRoomById(Integer roomId) {

    }
}
