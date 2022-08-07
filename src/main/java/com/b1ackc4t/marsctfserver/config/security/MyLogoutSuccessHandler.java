package com.b1ackc4t.marsctfserver.config.security;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.service.impl.TokenServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    TokenServiceImpl tokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUserDetails myUserDetails = tokenService.getLoginUser(request);
        if (myUserDetails != null) {
            String tokenId = myUserDetails.getTokenId();
            String username = myUserDetails.getUsername();
            Integer uid = myUserDetails.getUid();
            tokenService.delLoginUser(tokenId);
            log.info("id={}, username={} 退出登录状态", uid, username);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag", true);
        jsonObject.put("data", "退出登录成功");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonObject.toJSONString());
    }
}
