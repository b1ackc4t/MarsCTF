package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChallengeMapperTest {

    @Autowired
    ChallengeMapper challengeMapper;

    @Test
    void fun() {
        List<Challenge> pageInfo = challengeMapper.selectAllForUser(105355682);
        System.out.println();
    }
}