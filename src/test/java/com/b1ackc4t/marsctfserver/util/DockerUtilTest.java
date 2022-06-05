package com.b1ackc4t.marsctfserver.util;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.DockerCmdExecFactory;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.jaxrs.JerseyDockerCmdExecFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class DockerUtilTest {
    @Test
    void fun01() {

    }

    @Test
    void fun02() throws InterruptedException {

        DockerUtil.removeContainer("dcbf3e7cd1d3a9d5f745e541f57637442c2d6d9ce251d5a1d9c4d724673742f9");
    }
}