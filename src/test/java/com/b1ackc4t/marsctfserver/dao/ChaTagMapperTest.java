package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChaTagMapperTest {

    @Autowired
    ChaTagMapper chaTagMapper;
    @Test
    void fun01() {
        List<ChaTag> list = chaTagMapper.selectAllTag();
        System.out.println();
    }
}