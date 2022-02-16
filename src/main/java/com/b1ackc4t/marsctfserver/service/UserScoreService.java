package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.github.pagehelper.PageInfo;

public interface UserScoreService {
    PageInfo<User> getUserScoreByPage(int pageNum, int pageSize);
    /**
     * 获取排行榜信息
     * @param cid 题目id
     * @param rankNum 排名榜总人数
     * @return
     */
    ReturnRes getChaRanking(Integer cid, Integer rankNum);
    ReturnRes getScoreChart(Integer uid);
}
