package com.b1ackc4t.marsctfserver.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : b1ackc4t
 * @date : 2022/8/7 18:18
 */
@SpringBootTest
class RedisCacheTest {
    @Autowired
    RedisCache redisCache;
    @Test
    void fun() {
        redisCache.setCacheObject("fwaf", "fwafwa");
        redisCache.getCacheObject("LOGIN_KEYe0336957-c860-4ab9-b3f6-1dad279c4e27");
        System.out.println();
    }

}