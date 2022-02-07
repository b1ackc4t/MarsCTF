package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaRankPojo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserScoreMapperTest {
    @Autowired
    UserScoreMapper userScoreMapper;
    @Test
    void fun01() {
        List<ChaRankPojo> list = userScoreMapper.selectChaRankInfo(42, 4);
        System.out.println();
    }
}