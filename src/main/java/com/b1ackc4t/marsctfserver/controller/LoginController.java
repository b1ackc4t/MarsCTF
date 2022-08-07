package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.impl.LoginServiceImpl;
import com.b1ackc4t.marsctfserver.service.impl.TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : b1ackc4t
 * @date : 2022/8/7 14:25
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {



    final LoginServiceImpl loginService;

    @Autowired
    public LoginController(LoginServiceImpl loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ReturnRes login(@RequestParam String username,
                           @RequestParam String password) {
        String token = loginService.login(username, password);
        return new ReturnRes(true, token);
    }
}
