package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.LearnTagMap;
import com.b1ackc4t.marsctfserver.pojo.Learning;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LearnTagMapService extends IService<LearnTagMap> {
    void saveLearnTagMap(LearnTagMap learnTagMap);
}
