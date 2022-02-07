package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.UserMapper;
import com.b1ackc4t.marsctfserver.dao.UserScoreMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaRankPojo;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.UserScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserScoreServiceImpl implements UserScoreService {

    final UserScoreMapper userScoreMapper;

    public UserScoreServiceImpl(UserScoreMapper userScoreMapper) {
        this.userScoreMapper = userScoreMapper;
    }

    @Override
    public PageInfo<User> getUserScoreByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userScoreMapper.selectAllScore();
        return new PageInfo<>(list);
    }

    @Override
    public ReturnRes getChaRanking(Integer cid, Integer rankNum) {
        if (rankNum < 1) return new ReturnRes(false, "参数不合法");
        List<ChaRankPojo> chaRankPojos = userScoreMapper.selectChaRankInfo(cid, rankNum);
        if (chaRankPojos != null) {
            return new ReturnRes(true, chaRankPojos, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }
}
