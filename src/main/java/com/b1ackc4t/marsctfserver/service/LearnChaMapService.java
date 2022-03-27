package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.LearnChaMap;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LearnChaMapService extends IService<LearnChaMap> {
    void saveLearnChaMap(LearnChaMap learnChaMap);
}
