package com.b1ackc4t.marsctfserver.util;

import com.b1ackc4t.marsctfserver.dao.ConfigMapper;
import com.b1ackc4t.marsctfserver.pojo.Config;
import com.b1ackc4t.marsctfserver.pojo.DockerContainer;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.command.PullImageResultCallback;
import com.github.dockerjava.api.exception.NotFoundException;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.springframework.context.ApplicationContext;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DockerUtil {

    private static DockerClient dockerClient = createClient();

    private static DockerClient createClient() {
        ApplicationContext context = SpringUtil.getApplicationContext();
        ConfigMapper configMapper = context.getBean(ConfigMapper.class);// 注意是Service，不是ServiceImpl
        Config panelConfig = configMapper.selectDockerUtilConfig();
        DockerClientConfig config = null;
        if (panelConfig.getIsTls()) {
            Map<String, String> ymlByFileName = YmlUtil.getYmlByFileName(YmlUtil.bootstrap_file,"marsctf");
            config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(panelConfig.getDockerHost())
                    .withDockerTlsVerify(true)
                    // 证书的本地位置
                    .withDockerCertPath(ymlByFileName.get("marsctf.dockerCertKeyPath"))
                    // 私钥的本地位置
                    .withDockerConfig(ymlByFileName.get("marsctf.dockerCertKeyPath"))
                    .withRegistryUrl(panelConfig.getDockerRegistry())
                    .build();
        } else {
            config = DefaultDockerClientConfig.createDefaultConfigBuilder()
                    .withDockerHost(panelConfig.getDockerHost())
                    .withRegistryUrl(panelConfig.getDockerRegistry())
                    .build();
        }
        return DockerClientBuilder.getInstance(config).build();
    }

    public static void updateClient() {
        dockerClient = createClient();
    }

    public static Info info() {
        return dockerClient.infoCmd().exec();
    }

    public static List<SearchItem> searchDocker(String keyword) {
        return dockerClient.searchImagesCmd(keyword).exec();
    }

    public static void pullImage(String imageName, PullImageResultCallback pullImageResultCallback) throws InterruptedException {
        dockerClient.pullImageCmd(imageName).exec(pullImageResultCallback);
    }

    public static void removeImage(String imageName) {
        dockerClient.removeImageCmd(imageName)
                .withForce(true)
                .exec();
    }

    public static CreateContainerResponse createContainer(DockerContainer dockerContainer) {
        PortBinding portBinding = PortBinding.parse(dockerContainer.getDstPort() + ":" + dockerContainer.getSrcPort());
        HostConfig hostConfig = HostConfig.newHostConfig()
                .withMemory(dockerContainer.getMemLimit() * 1024 * 1024L)
                .withCpuCount((int)(dockerContainer.getCpuLimit()*100) * 10000000L)
                .withPortBindings(portBinding);
        CreateContainerResponse container = dockerClient.createContainerCmd(dockerContainer.getImageName())
                .withName(dockerContainer.getName())
                .withHostConfig(hostConfig)
                .withEnv((dockerContainer.getFlag() != null)?String.format("%s=%s", "FLAG", dockerContainer.getFlag()):"FLAG=hubu_mars")
                .withExposedPorts(ExposedPort.parse(dockerContainer.getSrcPort() + "/tcp"))
                .exec();
        return container;
    }

    public static void startContainer(String id) {
        dockerClient.startContainerCmd(id).exec();
    }

    public static void stopContainer(String id) {
        dockerClient.stopContainerCmd(id).exec();
    }

    public static void removeContainer(String id) {
        dockerClient.removeContainerCmd(id)
                .withForce(true)
                .exec();
    }

    public static Boolean checkImage(String imageName) {
        try {
            InspectImageResponse inspectImageResponse = dockerClient.inspectImageCmd(imageName).exec();
            return true;
        } catch (NotFoundException e) {
            return false;
        }

    }

    public static String[] resolveImageName(String imageName) {
        String pattern = "(.*):(.*)";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(imageName);
        String[] res = new String[3];
        while (m.find()) {
            res[0] = imageName;
            res[1] = m.group(1);
            res[2] = m.group(2);
            return res;
        }
        res[0] = imageName + ":latest";
        res[1] = imageName;
        res[2] = "latest";
        return res;
    }

    public static void fun() {
        dockerClient.pullImageCmd("hello-world").exec(new ResultCallback<PullResponseItem>() {
            public void onStart(Closeable closeable) {
                System.out.println("start");
            }

            public void onNext(PullResponseItem object) {

                System.out.println("next" +object.getStatus());
            }

            public void onError(Throwable throwable) {
                System.out.println("error");
                throwable.printStackTrace();
            }

            public void onComplete() {
                System.out.println("pull finished");
            }

            public void close() throws IOException {
                System.out.println("close");
            }
        });
    }


}
