package com.b1ackc4t.marsctfserver.config.security;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("flag", false);
        jsonObject.put("msg", "please login firstly");
        response.getWriter().write(jsonObject.toJSONString());
    }
}
