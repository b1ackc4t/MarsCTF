package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.Config;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ConfigService extends IService<Config> {
    ReturnRes getConfig();
    ReturnRes getDefault();
    ReturnRes updateConfig(Config config);
}
