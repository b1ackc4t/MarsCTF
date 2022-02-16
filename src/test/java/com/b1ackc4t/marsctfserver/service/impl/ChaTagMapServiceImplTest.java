package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.pojo.ChaTagMap;
import com.b1ackc4t.marsctfserver.service.ChaTagMapService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChaTagMapServiceImplTest {

    @Autowired
    ChaTagMapService chaTagMapService;

    @Test
    void fun01() {
        List<ChaTagMap> list = chaTagMapService.getTgnameByCid(2);
        System.out.println();
    }

    @Test
    void fun02() {

    }

    @Test
    void fun03() {
        chaTagMapService.removeByCid(1);
        System.out.println();
    }
}