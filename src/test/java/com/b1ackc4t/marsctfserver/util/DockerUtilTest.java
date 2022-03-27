package com.b1ackc4t.marsctfserver.util;

import com.github.dockerjava.api.model.SearchItem;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DockerUtilTest {
    @Test
    void fun01() {
        List<SearchItem> list = DockerUtil.searchDocker("library/hello-world");
        System.out.println();
    }

    @Test
    void fun02() throws InterruptedException {

        DockerUtil.removeContainer("dcbf3e7cd1d3a9d5f745e541f57637442c2d6d9ce251d5a1d9c4d724673742f9");
    }
}