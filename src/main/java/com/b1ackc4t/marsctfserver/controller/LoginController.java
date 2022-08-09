package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.impl.LoginServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
                           @RequestParam String password,
                           @RequestParam String captchaId,
                           @RequestParam String captcha) {
        if (loginService.validateCaptcha(username, captcha, captchaId)) {
            String token = loginService.login(username, password);
            if (token != null) {
                return new ReturnRes(true, token, "登录成功");
            } else {
                return new ReturnRes(false, "用户名或密码错误");
            }
        }
        return new ReturnRes(false, "验证码输入错误");


    }
}
