package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ConfigMapper;
import com.b1ackc4t.marsctfserver.pojo.Config;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ConfigService;
import com.b1ackc4t.marsctfserver.util.DockerUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.dockerjava.api.model.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {
    final ConfigMapper configMapper;

    @Autowired
    public ConfigServiceImpl(ConfigMapper configMapper) {
        this.configMapper = configMapper;
    }

    @Override
    public ReturnRes getConfig() {
        Config config = configMapper.selectConfig();
        if (config != null) {
            return new ReturnRes(true, config, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getDefault() {
        Config config = new Config();
        config.setDockerMinPort(40000);
        config.setDockerMaxPort(50000);
        config.setDockerTime(7200);
        config.setMaxContainerCount(100);
        config.setAddTimeCount(3);
        config.setAddTimeNum(3600);
        config.setUserMaxContainer(1);
        config.setIsTls(false);
        config.setDockerHost("tcp://127.0.0.1:2375");
        config.setDockerRegistry("http://hub-mirror.c.163.com");

        config.setIsMulti(false);
        config.setFrpcApiHost("127.0.0.1");
        config.setRetDomain("127.0.0.1");
        config.setFrpcApiPort("7400");
        config.setFrpcApiUser("admin");
        config.setFrpcApiPass("admin");
        config.setFrpcConfig("[common]\n" +
                "server_addr = 192.168.48.1\n" +
                "server_port = 7000\n" +
                "token = b1ackc4tyyds\n" +
                "admin_addr = 127.0.0.1\n" +
                "admin_port = 7400\n" +
                "admin_user = admin\n" +
                "admin_pwd = admin\n" +
                "\n" +
                "[admin_ui]\n" +
                "type = tcp\n" +
                "local_port = 7400\n" +
                "remote_port = 7400");
        return new ReturnRes(true, config, "查询成功");
    }

    @Override
    public ReturnRes updateConfig(Config config) {
        if (update(config, null)) {
            DockerUtil.updateClient();
            try {
                Info info = DockerUtil.info();
                return new ReturnRes(true, "修改成功 docker API有效");
            } catch (Exception e) {
                e.printStackTrace();
                return new ReturnRes(false, "修改成功 但docker API无效！");
            }
        }
        return new ReturnRes(false, "修改失败");
    }
}
