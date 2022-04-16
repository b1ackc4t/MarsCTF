package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.*;
import com.b1ackc4t.marsctfserver.pojo.*;
import com.b1ackc4t.marsctfserver.service.LearnChaMapService;
import com.b1ackc4t.marsctfserver.service.LearnImageService;
import com.b1ackc4t.marsctfserver.service.LearnTagMapService;
import com.b1ackc4t.marsctfserver.service.LearningService;
import com.b1ackc4t.marsctfserver.util.CommonUtil;
import com.b1ackc4t.marsctfserver.util.MdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class LearningServiceImpl extends ServiceImpl<LearningMapper, Learning> implements LearningService {
    final LearnTagMapService learnTagMapService;
    final LearnChaMapService learnChaMapService;
    final LearningMapper learningMapper;
    final LearnChaMapMapper learnChaMapMapper;
    final LearnTagMapMapper learnTagMapMapper;
    final LearnImageService learnImageService;
    final LearnImageMapper learnImageMapper;
    final LearnCommentMapper learnCommentMapper;

    @Autowired
    public LearningServiceImpl(LearnTagMapService learnTagMapService, LearnChaMapService learnChaMapService, LearningMapper learningMapper, LearnChaMapMapper learnChaMapMapper, LearnTagMapMapper learnTagMapMapper, LearnImageService learnImageService, LearnImageMapper learnImageMapper, LearnCommentMapper learnCommentMapper) {
        this.learnTagMapService = learnTagMapService;
        this.learnChaMapService = learnChaMapService;
        this.learningMapper = learningMapper;
        this.learnChaMapMapper = learnChaMapMapper;
        this.learnTagMapMapper = learnTagMapMapper;
        this.learnImageService = learnImageService;
        this.learnImageMapper = learnImageMapper;
        this.learnCommentMapper = learnCommentMapper;
    }

    @Override
    public ReturnRes saveLearning(Learning learning) {
        learning.setCreTime(new Date());    // 设置初始值
        learning.setDescr(MdUtil.getDescrByMd(learning.getText()));
        learning.setLid(null);

        List<Integer> tgids = learning.getTgids();
        List<Integer> cids = learning.getCids();

        learning.setTgids(null);
        learning.setCids(null);

        if (save(learning)) {
            if (tgids != null) {
                for (Integer tgid : tgids) {
                    learnTagMapService.saveLearnTagMap(new LearnTagMap(learning.getLid(), tgid));
                }
            }
            if (learning.getHasTrain()) {
                if (cids != null) {
                    for (Integer cid : cids) {
                        learnChaMapService.saveLearnChaMap(new LearnChaMap(learning.getLid(), cid));
                    }
                }
            }
            learnImageMapper.updateImageInfo(learning.getLid());
            return new ReturnRes(true, learning.getTitle() + " 添加成功");
        }
        return new ReturnRes(false, "添加失败");
    }

    @Override
    public ReturnRes getLearningByPageForAdmin(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Learning> list = learningMapper.selectAllLearningForAdmin();
        if (list != null) {
            return new ReturnRes(true, new PageInfo<>(list), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes removeLearning(Learning learning) {
        Integer lid = learning.getLid();
        learnTagMapMapper.deleteLearnTagMapByLid(lid);
        learnChaMapMapper.deleteLearnChaMapByLid(lid);
        learnCommentMapper.deleteByLid(lid);
        if (removeById(lid)) {
            learnImageService.removeLearnImageByLid(lid);
            return new ReturnRes(true, learning.getTitle() + " 删除成功");
        }
        return new ReturnRes(false, learning.getTitle() + " 删除失败");
    }

    @Override
    public ReturnRes updateLearning(Learning learning) {
        Integer lid = learning.getLid();
        List<Integer> tgids = learning.getTgids();
        List<Integer> cids = learning.getCids();
        if (learning.getText() != null) {
            learning.setDescr(MdUtil.getDescrByMd(learning.getText()));
        } else {
            learning.setDescr(null);
        }

        learning.setTgids(null);
        learning.setCids(null);
        learning.setCreTime(null);
        learning.setTname(null);

        if (updateById(learning)) {
            if (tgids != null) {
                learnTagMapMapper.deleteLearnTagMapByLid(lid);
                for (Integer tgid : tgids) {
                    learnTagMapService.saveLearnTagMap(new LearnTagMap(lid, tgid));
                }
            }
            learnChaMapMapper.deleteLearnChaMapByLid(lid);
            if (learning.getHasTrain()) {
                if (cids != null) {
                    for (Integer cid : cids) {
                        learnChaMapService.saveLearnChaMap(new LearnChaMap(lid, cid));
                    }
                }
            }
            learnImageMapper.updateImageInfo(lid);
            return new ReturnRes(true, "修改成功");
        }
        return new ReturnRes(false, "修改失败");
    }

    @Override
    public ReturnRes getLearningByLidForAdminEdit(Integer lid) {
        Learning learning = learningMapper.selectLearningByLidForAdminEdit(lid);
        if (learning != null) {
            learning.setTgids(learnTagMapMapper.selectTgidByLid(lid));
            if (learning.getHasTrain()) {
                learning.setCids(learnChaMapMapper.selectCidByLid(lid));
            }
            return new ReturnRes(true, learning, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    public List<Learning> fillLearningsTag(List<Learning> list) {
        for (Learning learning : list) {
            List<ChaTag> tmp = learnTagMapMapper.selectTgByLid(learning.getLid());
            List<Integer> tgids = new ArrayList<>();
            List<String> tgnames = new ArrayList<>();
            for (ChaTag tag : tmp) {
                tgids.add(tag.getTgid());
                tgnames.add(tag.getTgname());
            }
            learning.setTgids(tgids);
            learning.setTgnames(tgnames);
        }
        return list;
    }

    public Learning fillLearning(Learning learning) {
        List<ChaTag> tmp = learnTagMapMapper.selectTgByLid(learning.getLid());
        List<Integer> tgids = new ArrayList<>();
        List<String> tgnames = new ArrayList<>();
        for (ChaTag tag : tmp) {
            tgids.add(tag.getTgid());
            tgnames.add(tag.getTgname());
        }
        learning.setTgids(tgids);
        learning.setTgnames(tgnames);
        if (learning.getHasTrain()) {
            learning.setCids(learnChaMapMapper.selectCidByLid(learning.getLid()));
        }
        return learning;
    }

    @Override
    public ReturnRes getLearningByPageForUser(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Learning> list = learningMapper.selectAllLearningForUser();
        if (list != null) {
            fillLearningsTag(list);
            return new ReturnRes(true, new PageInfo<>(list), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getLearningByTypeForUser(int pageSize, int pageNum, String tname) {
        PageHelper.startPage(pageNum, pageSize);
        List<Learning> list = learningMapper.selectLearningByTypeForUser(tname);
        if (list != null) {
            fillLearningsTag(list);
            return new ReturnRes(true, new PageInfo<>(list), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getLearningByTagForUser(int pageSize, int pageNum, String tgname) {
        PageHelper.startPage(pageNum, pageSize);
        List<Learning> list = learningMapper.selectLearningByTagForUser(tgname);
        if (list != null) {
            fillLearningsTag(list);
            return new ReturnRes(true, new PageInfo<>(list), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getLearningByLidForUser(Integer lid) {
        Learning learning = learningMapper.selectLearningByLidForUser(lid);
        if (learning != null) {
            learning = fillLearning(learning);
            return new ReturnRes(true, learning, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getChallengeByLidForUser(Integer uid, Integer lid) {
        List<Challenge> challenges = learningMapper.selectChallengeByLidForUser(uid, lid);
        if (challenges != null) {
            return new ReturnRes(true, challenges, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes searchLearnPage(String key, String value, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        String[] whiteList = new String[]{"lid", "uname", "title", "tname", "creTime"};
        if (CommonUtil.strArrayIsHave(whiteList, key)) {
            List<Learning> list = learningMapper.selectSearchLearningForAdmin(key, "%" + value + "%");
            if (list != null) {
                return new ReturnRes(true, new PageInfo<>(list), "查询成功");
            }
            return new ReturnRes(false, "查询失败");
        } else {
            return new ReturnRes(false, "师傅请勿尝试不安全的参数");
        }

    }
}
