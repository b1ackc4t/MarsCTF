package com.b1ackc4t.marsctfserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.UserService;
import com.b1ackc4t.marsctfserver.util.generator.ShortUUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    UserService userService;

    @Test
    void fun01() {
        User user = new User();
        user.setUname("admin");
        user.setUpassword(new BCryptPasswordEncoder().encode("123456"));
        user.setRole("admin");
        user.setUid(ShortUUID.generateUid());
        try {
            userService.save(user);
        } catch (DuplicateKeyException e) {
            System.out.println(e.toString());
        }
    }

    @Test
    void fun02() {
        boolean b = userService.usernameIsRepeat("admin");
        System.out.println(b);
    }

    @Test
    void fun03() {
        User user = new User();
        user.setUname("xsxsf");
        user.setUpassword("safwa");
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    void fun04() {
        User user = new User();
        user.setUid(111);
        userService.removeById(user);
    }

    @Test
    void fun05() {
        String s = "fwafawf.jpg";
        int index = s.lastIndexOf('.');
        System.out.println(s.substring(index));
    }
}