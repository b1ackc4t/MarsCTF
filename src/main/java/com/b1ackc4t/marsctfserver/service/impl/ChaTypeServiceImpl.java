package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChaTypeMapper;
import com.b1ackc4t.marsctfserver.dao.LearningMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.b1ackc4t.marsctfserver.pojo.Learning;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaTagService;
import com.b1ackc4t.marsctfserver.service.ChaTypeService;
import com.b1ackc4t.marsctfserver.service.ChallengeService;
import com.b1ackc4t.marsctfserver.service.LearningService;
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
    final LearningMapper learningMapper;
    final LearningService learningService;

    public ChaTypeServiceImpl(ChaTypeMapper chaTypeMapper, ChaTagService chaTagService, ChallengeService challengeService, LearningMapper learningMapper, LearningService learningService) {
        this.chaTypeMapper = chaTypeMapper;
        this.chaTagService = chaTagService;
        this.challengeService = challengeService;
        this.learningMapper = learningMapper;
        this.learningService = learningService;
    }

    @Override
    public ReturnRes getTypesByPageForAdmin(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<ChaType> chaTypes = chaTypeMapper.selectAllForAdmin();
        if (chaTypes != null) {
            return new ReturnRes(true, new PageInfo<>(chaTypes), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes updateType(ChaType chaType) {
        chaType.setChaNum(null);
        chaType.setLearnNum(null);
        if (updateById(chaType)) {
            return new ReturnRes(true, "成功修改为" + chaType.getTname());
        }
        return new ReturnRes(false, "修改失败");
    }

    @Override
    public ReturnRes saveType(ChaType chaType) {
        chaType.setTid(null);
        chaType.setChaNum(null);
        chaType.setLearnNum(null);
        if (save(chaType)) {
            return new ReturnRes(true, chaType.getTname() + " 添加成功");
        }
        return new ReturnRes(false, "添加失败");
    }

    @Override
    public ReturnRes removeType(ChaType chaType) {
        List<Integer> list = learningMapper.selectLidByTid(chaType.getTid());
        for (Integer lid : list) {
            learningService.removeLearning(new Learning(lid));
        }
        list = chaTypeMapper.selectTgidByTid(chaType.getTid());
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
