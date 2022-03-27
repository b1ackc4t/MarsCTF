package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.b1ackc4t.marsctfserver.pojo.Config;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ConfigController {
    final ConfigService configService;

    @Autowired
    public ConfigController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/admin/config")
    public ReturnRes getAll() {
        return configService.getConfig();
    }

    @GetMapping("/admin/configDefault")
    public ReturnRes getDefault() {
        return configService.getDefault();
    }

    @PostMapping("/admin/config")
    public ReturnRes updateConfig(@RequestParam(name = "data") String configJson) {
        Config config = JSONObject.parseObject(configJson, Config.class);
        return configService.updateConfig(config);
    }
}
