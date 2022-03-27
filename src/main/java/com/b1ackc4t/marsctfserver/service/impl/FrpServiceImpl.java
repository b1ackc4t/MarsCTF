package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ConfigMapper;
import com.b1ackc4t.marsctfserver.dao.DockerContainerMapper;
import com.b1ackc4t.marsctfserver.pojo.Config;
import com.b1ackc4t.marsctfserver.pojo.DockerContainer;
import com.b1ackc4t.marsctfserver.service.FrpService;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Base64;

@Service
public class FrpServiceImpl implements FrpService {

    final ConfigMapper configMapper;
    final DockerContainerMapper dockerContainerMapper;

    public FrpServiceImpl(ConfigMapper configMapper, DockerContainerMapper dockerContainerMapper) {
        this.configMapper = configMapper;
        this.dockerContainerMapper = dockerContainerMapper;
    }


    @Override
    public void flushFrpConfig() {
        Config config = configMapper.selectConfig();
        String frpcConfig = config.getFrpcConfig();
        StringBuilder stringBuilder = new StringBuilder();
        String directTemp = "\n\n[direct_%s_tcp]\n" +
                "type = tcp\n" +
                "local_ip = %s\n" +
                "local_port = %s\n" +
                "remote_port = %s\n" +
                "\n\n[direct_%s_udp]\n" +
                "type = udp\n" +
                "local_ip = %s\n" +
                "local_port = %s\n" +
                "remote_port = %s\n";
        stringBuilder.append(frpcConfig);
        List<DockerContainer> dockerContainers = dockerContainerMapper.selectAllContainer();
        for (DockerContainer dockerContainer : dockerContainers) {
            stringBuilder.append(String.format(directTemp, dockerContainer.getDstPort(), "127.0.0.1", dockerContainer.getDstPort(), dockerContainer.getDstPort(), dockerContainer.getDstPort(), "127.0.0.1", dockerContainer.getDstPort(), dockerContainer.getDstPort()));
        }
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPut httpPut = new HttpPut(String.format("http://%s:%s/api/config", config.getFrpcApiHost(), config.getFrpcApiPort()));
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
            httpPut.setConfig(requestConfig);
            httpPut.setEntity(new StringEntity(stringBuilder.toString()));
            String auth = config.getFrpcApiUser() + ":" + config.getFrpcApiPass();
            byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes());
            String authHeader = "Basic " + new String(encodedAuth);
            httpPut.addHeader("Authorization", authHeader);
            httpClient.execute(httpPut);
            HttpGet httpGet = new HttpGet(String.format("http://%s:%s/api/reload", config.getFrpcApiHost(), config.getFrpcApiPort()));
            httpGet.setConfig(requestConfig);
            httpGet.addHeader("Authorization", authHeader);
            httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
