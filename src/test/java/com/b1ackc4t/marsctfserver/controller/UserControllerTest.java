package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserController userController;

    @Test
    void fun01() {
        Pattern r = Pattern.compile("\\w+");
        Matcher m = r.matcher("fwgwgw");

        System.out.println("lookingAt(): "+m.lookingAt());	// 尝试从开头匹配
        System.out.println("matches(): "+m.matches());
    }

}