package com.b1ackc4t.marsctfserver.controller;

import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
public class Simple {

    @RequestMapping("/xxx")
    public Object xxx(SecurityExpressionRoot securityExpressionRoot) {
        return securityExpressionRoot.getPrincipal();
    }

    @RequestMapping("/aaa")
    public String fun01() throws Exception {
        if (true) throw new Exception();
        return "1111";
    }
}
