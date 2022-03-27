package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.service.DockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class Simple {

    @Autowired
    DockerService dockerService;

    @RequestMapping("/xxx")
    public Object xxx(SecurityExpressionRoot securityExpressionRoot) {
        return securityExpressionRoot.getPrincipal();
    }

    @RequestMapping("/aaa")
    public String fun01() throws Exception {
        return "1111";
    }
}
