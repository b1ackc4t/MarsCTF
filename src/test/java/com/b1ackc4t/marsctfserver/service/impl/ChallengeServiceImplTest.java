package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.service.ChallengeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChallengeServiceImplTest {

    @Autowired
    ChallengeService challengeService;

    @Test
    void fun01() {
        Challenge challenge = new Challenge();
        challenge.setCname("daswf");
        challenge.setTname("web");
        challenge.setDescr("xcsafc");
        challenge.setScore(200);
        challenge.setFlag("fawf");
        challenge.setExposed(true);
        challengeService.save(challenge);
    }
}