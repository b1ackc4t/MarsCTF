package com.b1ackc4t.marsctfserver.config.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag", true);
        jsonObject.put("data", "");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(jsonObject.toJSONString());
    }
}
