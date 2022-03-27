package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.LearnChaMapMapper;
import com.b1ackc4t.marsctfserver.pojo.LearnChaMap;
import com.b1ackc4t.marsctfserver.service.LearnChaMapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LearnChaMapServiceImpl extends ServiceImpl<LearnChaMapMapper, LearnChaMap> implements LearnChaMapService {
    @Override
    public void saveLearnChaMap(LearnChaMap learnChaMap) {
        save(learnChaMap);
    }
}
