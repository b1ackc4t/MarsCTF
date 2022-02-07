package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.UserScoreService;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserScoreServiceImplTest {
    @Autowired
    UserScoreService userScoreService;

    @Test
    void fun01() {
        PageInfo<User> list = userScoreService.getUserScoreByPage(2, 4);
        System.out.println();
    }
}