package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.DockerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class DockerServiceImplTest {

    @Autowired
    DockerService dockerService;

    @Test
    void fun01() throws InterruptedException {
        Challenge challenge = new Challenge();
        challenge.setImageName("ctftraining/qwb_2019_supersqli");
        challenge.setSrcPort(80);
//        ReturnRes returnRes = dockerService.addDockerForCha(challenge.getImageName());
    }

    @Test
    void fun02() throws InterruptedException, IOException {
        Challenge challenge = new Challenge();
        challenge.setImageName("ctftraining/qwb_2019_supersqli");
        challenge.setSrcPort(80);
    }

    @Test
    void fun03() throws InterruptedException {
        Challenge challenge = new Challenge();
    }

    @Test
    void fun04() throws InterruptedException {
        Challenge challenge = new Challenge();
    }

    @Test
    void fun05() throws InterruptedException, IOException {
        Challenge challenge = new Challenge();
        ReturnRes returnRes = dockerService.getContainerByCidUid(12,31);
        System.out.println();
    }
}