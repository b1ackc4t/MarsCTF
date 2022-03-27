package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Config;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ConfigMapperTest {
    @Autowired
    ConfigMapper configMapper;
    @Test
    void fun01() {
        Config config = configMapper.selectConfig();
        System.out.println();
    }
}