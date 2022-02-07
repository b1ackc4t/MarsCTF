package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserController userController;

    @Test
    void fun01() {
        User user = new User();
        user.setUname("xsh11");
        user.setUpassword("rr12");
        userController.save(user);
    }

}