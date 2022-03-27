package com.b1ackc4t.marsctfserver.util;

import com.b1ackc4t.marsctfserver.dao.ConfigMapper;
import com.b1ackc4t.marsctfserver.dao.DockerContainerMapper;
import com.b1ackc4t.marsctfserver.pojo.DockerContainer;
import com.b1ackc4t.marsctfserver.service.DockerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Component
public class Scheduler {

    final DockerContainerMapper dockerContainerMapper;
    final ConfigMapper configMapper;
    final DockerService dockerService;

    public Scheduler(DockerContainerMapper dockerContainerMapper, ConfigMapper configMapper, DockerService dockerService) {
        this.dockerContainerMapper = dockerContainerMapper;
        this.configMapper = configMapper;
        this.dockerService = dockerService;
    }

    //每隔5秒执行一次
    @Scheduled(fixedRate = 5000)
    public void testTasks() {
        List<DockerContainer> dockerContainers = dockerContainerMapper.selectTimeInfo();
        Integer dockerTime = configMapper.selectTimeConfig();
        for (DockerContainer dockerContainer : dockerContainers) {

            if (dockerContainer.getStartTime().getTime() + dockerContainer.getAddTime() * 1000 + dockerTime * 1000 <= new Date().getTime()) {
                try {
                    dockerService.removeContainerByConId(dockerContainer.getContainerId());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
