package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChaTypeMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaTagService;
import com.b1ackc4t.marsctfserver.service.ChaTypeService;
import com.b1ackc4t.marsctfserver.service.ChallengeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaTypeServiceImpl extends ServiceImpl<ChaTypeMapper, ChaType> implements ChaTypeService {
    final ChaTypeMapper chaTypeMapper;
    final ChaTagService chaTagService;
    final ChallengeService challengeService;

    public ChaTypeServiceImpl(ChaTypeMapper chaTypeMapper, ChaTagService chaTagService, ChallengeService challengeService) {
        this.chaTypeMapper = chaTypeMapper;
        this.chaTagService = chaTagService;
        this.challengeService = challengeService;
    }

    @Override
    public ReturnRes getTypesByPageForAdmin(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<ChaType> chaTypes = chaTypeMapper.selectAll();
        if (chaTypes != null) {
            return new ReturnRes(true, new PageInfo<>(chaTypes), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes updateType(ChaType chaType) {
        if (updateById(chaType)) {
            return new ReturnRes(true, "成功修改为" + chaType.getTname());
        }
        return new ReturnRes(false, "修改失败");
    }

    @Override
    public ReturnRes saveType(ChaType chaType) {
        chaType.setTid(null);
        if (save(chaType)) {
            return new ReturnRes(true, chaType.getTname() + " 添加成功");
        }
        return new ReturnRes(false, "添加失败");
    }

    @Override
    public ReturnRes removeType(ChaType chaType) {
        List<Integer> list = chaTypeMapper.selectTgidByTid(chaType.getTid());
        for (Integer tgid : list) {
            chaTagService.removeTag(new ChaTag(tgid));
        }
        list = chaTypeMapper.selectCidByTid(chaType.getTid());
        for (Integer cid : list) {
            challengeService.remove(cid);
        }
        if (removeById(chaType.getTid())) {
            return new ReturnRes(true, chaType.getTname() + " 删除成功");
        }
        return new ReturnRes(false, "删除失败");
    }
}
