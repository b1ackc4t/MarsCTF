package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSON;
import com.b1ackc4t.marsctfserver.pojo.Challenge;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChallengeControllerTest {

    @Test
    void fun01() {
        Challenge challenge = new Challenge();
        challenge.setCid(1213);
        challenge.setCname("asfafwf");
        System.out.println(JSON.toJSONString(challenge));
    }

}