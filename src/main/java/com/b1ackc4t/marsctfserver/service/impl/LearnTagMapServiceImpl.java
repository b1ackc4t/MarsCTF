package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.LearnTagMapMapper;
import com.b1ackc4t.marsctfserver.pojo.LearnTagMap;
import com.b1ackc4t.marsctfserver.service.LearnTagMapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class LearnTagMapServiceImpl extends ServiceImpl<LearnTagMapMapper, LearnTagMap> implements LearnTagMapService {
    @Override
    public void saveLearnTagMap(LearnTagMap learnTagMap) {
        save(learnTagMap);
    }
}
