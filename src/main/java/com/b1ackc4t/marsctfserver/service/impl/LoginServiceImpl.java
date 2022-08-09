package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.common.RedisCache;
import com.b1ackc4t.marsctfserver.config.Constant;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author : b1ackc4t
 * @date : 2022/8/7 14:22
 */
@Service
@Slf4j
public class LoginServiceImpl {
    final AuthenticationManager authenticationManager;
    final TokenServiceImpl tokenService;
    final RedisCache redisCache;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, TokenServiceImpl tokenService, RedisCache redisCache) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.redisCache = redisCache;
    }

    public String login(String username, String password) {
        // 用户验证
        Authentication authentication = null;
        try
        {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        }
        catch (Exception e)
        {
            log.warn("username={} 尝试登录失败，用户名或密码错误", username);
            return null;
        }
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = myUserDetails.getUser();
        log.info("id={}, username={} 登录成功", user.getUid(), username);
        String token = tokenService.createToken(myUserDetails);
        return token;
    }

    public boolean validateCaptcha(String username, String captcha, String uuid)
    {
        if (StringUtils.hasLength(uuid) && StringUtils.hasLength(captcha)) {
            String verifyKey = Constant.CAPTCHA_KEY + uuid;
            String ans = redisCache.getCacheObject(verifyKey);
            redisCache.deleteObject(verifyKey);
            if (ans != null && ans.equals(captcha))
            {
                log.info("username={} {}验证码输入正确", username, captcha);
                return true;
            }
        }
        log.warn("username={} {}验证码输入错误", username, captcha);
        return false;
    }
}
