package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChaTagMapMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaTagMap;
import com.b1ackc4t.marsctfserver.service.ChaTagMapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaTagMapServiceImpl extends ServiceImpl<ChaTagMapMapper, ChaTagMap> implements ChaTagMapService {

    final ChaTagMapMapper chaTagMapMapper;

    public ChaTagMapServiceImpl(ChaTagMapMapper chaTagMapMapper) {
        this.chaTagMapMapper = chaTagMapMapper;
    }

    /**
     * 获取题目的所有标签
     * @param cid
     * @return
     */
    @Override
    public List<ChaTagMap> getTgnameByCid(Integer cid) {
        List<ChaTagMap> list = chaTagMapMapper.selectTgnameByCid(cid);
        if (list == null) return null;
        return list;
    }

    @Override
    public boolean removeByCid(Integer cid) {
        return chaTagMapMapper.deleteByCid(cid);
    }
}
