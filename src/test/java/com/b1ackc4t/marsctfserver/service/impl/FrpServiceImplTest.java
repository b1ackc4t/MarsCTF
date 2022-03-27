package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.service.FrpService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FrpServiceImplTest {

    @Autowired
    FrpService frpService;
    @Test
    void fun01() throws IOException {
        frpService.flushFrpConfig();
    }
}