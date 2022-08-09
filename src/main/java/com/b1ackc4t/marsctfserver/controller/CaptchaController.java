package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.common.RedisCache;
import com.b1ackc4t.marsctfserver.config.Constant;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.util.GraphicHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author : b1ackc4t
 * @date : 2022/8/9 7:38
 */
@RestController
@RequestMapping("/api")
public class CaptchaController {

    @Value("${marsctf.captcha.expireTime}")
    Integer expireTime;

    final RedisCache redisCache;

    @Autowired
    public CaptchaController(RedisCache redisCache) {
        this.redisCache = redisCache;
    }

    @GetMapping("getCaptchaImage")
    public ReturnRes getCaptchaImage() {
        String uuid = UUID.randomUUID().toString();
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        String captchaValue = GraphicHelper.create(fastByteArrayOutputStream);
        redisCache.setCacheObject(Constant.CAPTCHA_KEY + uuid, captchaValue, expireTime, TimeUnit.MINUTES);
        Map<String, String> res = new HashMap<>();
        res.put("captchaId", uuid);
        res.put("img", new String(Base64.getEncoder().encode(fastByteArrayOutputStream.toByteArray())));
        return new ReturnRes(true, res);
    }
}
