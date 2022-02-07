package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ChallengeService extends IService<Challenge> {
    String SUCCESS_STATUS = "success";  // 题目正确的标记
    String FAIL_STATUS = "fail";    // 题目错误的标记

    // 所有人都可以访问
    ReturnRes getForAllByPage(int pageSize, int pageNum);
    ReturnRes getForAllByPageByType(int pageSize, int pageNum, String type);
    ReturnRes getForAllByPageByTag(int pageSize, int pageNum, String tag);

    // User级别
    ReturnRes getForUserByPage(Integer uid, int pageSize, int pageNum);
    ReturnRes getForUserByPageByType(Integer uid, int pageSize, int pageNum, String type);
    ReturnRes getForUserByPageByTag(Integer uid, int pageSize, int pageNum, String tag);
    ReturnRes getByIdForUser(Integer cid);
    ReturnRes submitFlag(User user, Integer cid, String flag);

    // admin级别的方法
    boolean save(Challenge challenge);
    boolean update(Challenge challenge);
    boolean remove(Integer cid);
    List<Challenge> getAllForAdmin();
    Challenge getByIdForAdmin(Integer cid);
    PageInfo<Challenge> getAllChallengeByPage(int pageNum, int pageSize);


}
