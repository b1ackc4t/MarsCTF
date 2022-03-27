package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.DockerContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DockerContainerMapperTest {
    @Autowired
    DockerContainerMapper dockerContainerMapper;
    @Test
    void fun01() {
        List<DockerContainer> list = dockerContainerMapper.selectAllContainerForAdmin();
        System.out.println();
    }

}