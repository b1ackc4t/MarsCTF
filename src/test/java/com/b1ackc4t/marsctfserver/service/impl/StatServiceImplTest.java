package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.StatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatServiceImplTest {

    @Autowired
    StatService statService;
    @Test
    void fun01() {
        ReturnRes returnRes = statService.getInfoCount();
        System.out.println();
    }
}