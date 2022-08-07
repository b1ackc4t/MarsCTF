package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author : b1ackc4t
 * @date : 2022/8/7 14:22
 */
@Service
@Slf4j
public class LoginServiceImpl {
    final AuthenticationManager authenticationManager;
    final TokenServiceImpl tokenService;

    public LoginServiceImpl(AuthenticationManager authenticationManager, TokenServiceImpl tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
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
            throw e;
        }
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        User user = myUserDetails.getUser();
        log.info("id={}, username={} 登录成功", user.getUid(), username);
        String token = tokenService.createToken(myUserDetails);
        return token;
    }
}
