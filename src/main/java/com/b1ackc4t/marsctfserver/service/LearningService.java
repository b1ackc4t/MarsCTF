package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.Learning;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LearningService extends IService<Learning> {

    // user级别
    ReturnRes getLearningByPageForUser(int pageSize, int pageNum);
    ReturnRes getLearningByTypeForUser(int pageSize, int pageNum, String tname);
    ReturnRes getLearningByTagForUser(int pageSize, int pageNum, String tgname);
    ReturnRes getLearningByLidForUser(Integer lid);
    ReturnRes getChallengeByLidForUser(Integer uid, Integer lid);

    // admin级别
    ReturnRes saveLearning(Learning learning);
    ReturnRes getLearningByPageForAdmin(int pageSize, int pageNum);
    ReturnRes removeLearning(Learning learning);
    ReturnRes updateLearning(Learning learning);
    ReturnRes getLearningByLidForAdminEdit(Integer lid);
}
