package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChallengeMapper;
import com.b1ackc4t.marsctfserver.dao.LearnChaMapMapper;
import com.b1ackc4t.marsctfserver.pojo.*;
import com.b1ackc4t.marsctfserver.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ChallengeServiceImpl extends ServiceImpl<ChallengeMapper, Challenge> implements ChallengeService {
    final ChallengeMapper challengeMapper;
    final ChaTagMapService chaTagMapService;
    final CTFFileService ctfFileService;
    final UserChaMapService userChaMapService;
    final UserService userService;
    final WriteupService writeupService;
    final LearnChaMapMapper learnChaMapMapper;
    final DockerService dockerService;

    @Autowired
    public ChallengeServiceImpl(ChallengeMapper challengeMapper, ChaTagMapService chaTagMapService, CTFFileService ctfFileService, UserChaMapService userChaMapService, UserService userService, WriteupService writeupService, LearnChaMapMapper learnChaMapMapper, DockerService dockerService) {
        this.challengeMapper = challengeMapper;
        this.chaTagMapService = chaTagMapService;
        this.ctfFileService = ctfFileService;
        this.userChaMapService = userChaMapService;
        this.userService = userService;
        this.writeupService = writeupService;
        this.learnChaMapMapper = learnChaMapMapper;
        this.dockerService = dockerService;
    }

    @Override
    public ReturnRes getByIdForUser(Integer cid) {
        Challenge challenge = challengeMapper.selectByIdForUser(cid);
        if (challenge != null) {
            List<ChaTagMap> list = chaTagMapService.getTgnameByCid(challenge.getCid());
            challenge.setTagsView(chaTagMap2String(list));
            challenge.setTags(chaTagMap2Id(list));
            return new ReturnRes(true, challenge, "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }

    }

    /**
     * 新增题目
     * @param challenge
     * @return
     */
    @Override
    public boolean save(Challenge challenge) {
        List<Integer> tags = challenge.getTags();
        challenge.setTname(null);
        challenge.setMasterName(null);
        challenge.setTags(null);
        challenge.setCretime(new Date());
        challenge.setFinishedNum(0);
        challenge.setTagsView(null);
        if (challenge.getIsDynamic()) {
            challenge.setDownloadOk(0);
        } else {
            challenge.setDownloadOk(null);
        }
        if (super.save(challenge)) {
            if (tags != null) {
                for (Integer tag : tags) {
                    chaTagMapService.save(new ChaTagMap(challenge.getCid(), tag));
                }
            }
            if (challenge.getIsDynamic()) {
                try {
                    dockerService.addDockerForCha(challenge.getImageName(), challenge.getCid());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 修改题目信息
     * @param challenge
     * @return
     */
    @Override
    public boolean update(Challenge challenge) {
        challenge.setTname(null);
        challenge.setMasterName(null);
        challenge.setFinishedNum(null);
        challenge.setTagsView(null);
        if (challenge.getIsDynamic()) {
            challenge.setDownloadOk(0);
        } else {
            challenge.setDownloadOk(null);
        }
        Challenge originChallenge = challengeMapper.selectInfoByCid(challenge.getCid());
        Integer originFid = originChallenge != null ? originChallenge.getFid() : null;
        List<Integer> tags = challenge.getTags();
        challenge.setTags(null);
        if (super.updateById(challenge)) {
            try {   // 删除过去的镜像 添加上新的镜像
                if (originChallenge != null && originChallenge.getIsDynamic()) {
                    if (originChallenge.getDownloadOk() == 1) {
                        dockerService.removeDockerForCha(originChallenge.getImageName(), challenge.getCid());    // 移除题目相关的镜像以及开启的容器
                    }
                }
                if (challenge.getIsDynamic()) {
                    dockerService.addDockerForCha(challenge.getImageName(), challenge.getCid());
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            if (tags != null) {     // 更新标签
                chaTagMapService.removeByCid(challenge.getCid());
                for (Integer tag : tags) {
                    chaTagMapService.save(new ChaTagMap(challenge.getCid(), tag));
                }
            }
            Integer fid = challenge.getFid();   // 若更新了附件 需要把以前的题目附件删除
            if (originFid != null) {
                if (fid != null) {
                    if (!fid.equals(originFid)) {
                        ctfFileService.removeCTFFile(originFid);
                    }
                } else {
                    ctfFileService.removeCTFFile(originFid);
                }
            }

            return true;
        }
        return false;
    }

    /**
     * 根据cid删除题目
     * @param cid
     * @return
     */
    @Override
    public boolean remove(Integer cid) {
        chaTagMapService.removeByCid(cid);  // 移除题目相关的标签映射
        List<Integer> list = challengeMapper.selectWidByCid(cid);
        for (Integer wid : list) {
            writeupService.removeWriteupForAdmin(new Writeup(wid));
        }
        userChaMapService.removeByCid(cid); // 移除题目的提交信息
        learnChaMapMapper.deleteLearnChaMapByCid(cid);  // 移除learning中关联的题目信息
        Challenge challenge = challengeMapper.selectInfoByCid(cid);
        try {
            if (challenge.getDownloadOk() == 1) {
                dockerService.removeDockerForCha(challenge.getImageName(), cid);    // 移除题目相关的镜像以及开启的容器
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (super.removeById(cid)) {
            if (challenge != null) {    // 移除题目的附件
                Integer fid = challenge.getFid();
                if (fid != null) ctfFileService.removeCTFFile(fid);
            }
            return true;
        }
        return false;
    }

    @Override
    public List<Challenge> getAllForAdmin() {
        List<Challenge> result = challengeMapper.selectAllForAdmin();
        if (result != null) {
            for (Challenge item : result) {
                List<ChaTagMap> list = chaTagMapService.getTgnameByCid(item.getCid());
                if (list != null) {
                    item.setTagsView(chaTagMap2String(list));
                    item.setTags(chaTagMap2Id(list));
                }
            }
        }
        return result;
    }

    public List<String> chaTagMap2String(List<ChaTagMap> list) {
        if (list != null) {
            List<String> res = new ArrayList<>();
            for (ChaTagMap chaTagMap : list) {
                res.add(chaTagMap.getTgname());
            }
            return res;
        }
        return null;
    }

    public List<Integer> chaTagMap2Id(List<ChaTagMap> list) {
        if (list != null) {
            List<Integer> res = new ArrayList<>();
            for (ChaTagMap chaTagMap : list) {
                res.add(chaTagMap.getTgid());
            }
            return res;
        }
        return null;
    }

    @Override
    public PageInfo<Challenge> getAllChallengeByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Challenge> list = challengeMapper.selectAllForAdmin();
        if (list != null) return new PageInfo<>(list);
        return null;
    }

    /**
     * 根据id查询题目
     * @param cid
     * @return
     */
    @Override
    public Challenge getByIdForAdmin(Integer cid) {
        Challenge challenge = challengeMapper.selectByIdForAdmin(cid);
        if (challenge != null) {
            List<ChaTagMap> list = chaTagMapService.getTgnameByCid(challenge.getCid());
            if (list != null) {
                challenge.setTagsView(chaTagMap2String(list));
                challenge.setTags(chaTagMap2Id(list));
            }
        }
        return challenge;
    }

    public Boolean checkFlag(String flag, Integer uid, Integer cid) {
        Challenge challenge = challengeMapper.selectStaticFlag(cid);
        if (challenge.getIsDynamicFlag()) {
            DockerContainer dockerContainer = challengeMapper.selectDynamicFlag(uid, cid);
            if (dockerContainer != null) {
                return flag.equals(dockerContainer.getFlag());
            }
        } else {
            return flag.equals(challenge.getFlag());
        }
        return false;
    }

    /**
     * 用户提交flag
     * @param user
     * @param cid 题目id
     * @param flag
     * @return
     */
    @Override
    public ReturnRes submitFlag(User user, Integer cid, String flag) {
        Challenge challenge = challengeMapper.selectByIdForSubmitFlag(cid);
        if (challenge != null && user != null) {
            UserChaMap userChaMap = new UserChaMap();
            userChaMap.setCid(cid);
            userChaMap.setUid(user.getUid());
            userChaMap.setFinishTime(new Date());
            String status = userChaMapService.getStatusById(user.getUid(), cid);
            if (checkFlag(flag, user.getUid(), challenge.getCid())) {
                Challenge tmp = new Challenge();
                tmp.setCid(cid);
                if (status != null) {    // 是否是第一次提交这题的flag
                    if (status.equals(SUCCESS_STATUS)) {
                        return new ReturnRes(true, "您已经提交过flag，无需反复提交");
                    } else {
                        tmp.setFinishedNum(challenge.getFinishedNum() + 1);
                        updateById(tmp);
                        userChaMap.setRank(challenge.getFinishedNum() + 1);
                        userChaMap.setStatus(SUCCESS_STATUS);
                        userChaMapService.updateById(userChaMap);
                        setAllScore(user, challenge);
                        userService.updateById(user);
                        return new ReturnRes(true, "flag提交正确，恭喜师傅！！！");
                    }
                } else {    // 第一次提交flag
                    tmp.setFinishedNum(challenge.getFinishedNum() + 1);
                    updateById(tmp);
                    userChaMap.setRank(challenge.getFinishedNum() + 1);
                    userChaMap.setStatus(SUCCESS_STATUS);
                    userChaMapService.save(userChaMap);
                    setAllScore(user, challenge);
                    userService.updateById(user);
                    return new ReturnRes(true, "flag提交正确，恭喜师傅！！！");
                }
            } else {
                if (status != null) {    // 是否是第一次提交这题的flag
                    if (!status.equals(SUCCESS_STATUS)) {
                        userChaMap.setStatus(FAIL_STATUS);
                        userChaMapService.updateById(userChaMap);
                    }
                } else {
                    userChaMap.setStatus(FAIL_STATUS);
                    userChaMapService.save(userChaMap);
                }
                return new ReturnRes(false, "flag错误，是哪里出了问题？");
            }
        } else {
            return new ReturnRes(false, "用户或题目信息出错");
        }
    }

    /**
     * 加分
     * @param user
     * @param challenge
     */
    public void setAllScore(User user, Challenge challenge) {
        String type = challenge.getTname();
        int score = challenge.getScore();
        if (type.equals("web")) {
            user.setWeb(user.getWeb() + score);
        } else if (type.equals("pwn")) {
            user.setPwn(user.getPwn() + score);
        } else if (type.equals("re")) {
            user.setRe(user.getRe() + score);
        } else if (type.equals("crypto")) {
            user.setCrypto(user.getCrypto() + score);
        } else if (type.equals("misc")) {
            user.setMisc(user.getMisc() + score);
        } else {
            user.setOther(user.getOther() + score);
        }
        user.setScore(user.getScore() + score);
    }

    @Override
    public ReturnRes getForAllByPage(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Challenge> challenges = challengeMapper.selectAllForAll();
        if (challenges != null) {
            return new ReturnRes(true, new PageInfo<>(challenges), "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }
    }

    @Override
    public ReturnRes getForUserByPage(Integer uid, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Challenge> challenges = challengeMapper.selectAllForUser(uid);
        if (challenges != null) {
            return new ReturnRes(true, new PageInfo<>(challenges), "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }
    }

    @Override
    public ReturnRes getForUserByPageByType(Integer uid, int pageSize, int pageNum, String type) {
        PageHelper.startPage(pageNum, pageSize);
        List<Challenge> challenges = challengeMapper.selectForUserByType(uid, type);
        if (challenges != null) {
            return new ReturnRes(true, new PageInfo<>(challenges), "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }
    }

    @Override
    public ReturnRes getForUserByPageByTag(Integer uid, int pageSize, int pageNum, String tag) {
        PageHelper.startPage(pageNum, pageSize);
        List<Challenge> challenges = challengeMapper.selectForUserByTag(uid, tag);
        if (challenges != null) {
            return new ReturnRes(true, new PageInfo<>(challenges), "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }
    }

    @Override
    public ReturnRes getForAllByPageByType(int pageSize, int pageNum, String type) {
        PageHelper.startPage(pageNum, pageSize);
        List<Challenge> challenges = challengeMapper.selectForAllByType(type);
        if (challenges != null) {
            return new ReturnRes(true, new PageInfo<>(challenges), "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }
    }

    @Override
    public ReturnRes getForAllByPageByTag(int pageSize, int pageNum, String tag) {
        PageHelper.startPage(pageNum, pageSize);
        List<Challenge> challenges = challengeMapper.selectForAllByTag(tag);
        if (challenges != null) {
            return new ReturnRes(true, new PageInfo<>(challenges), "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }
    }

    @Override
    public ReturnRes getSimpleAll() {
        List<Challenge> challenges = challengeMapper.selectSimpleAll();
        if (challenges != null) {
            return new ReturnRes(true, challenges, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

}
