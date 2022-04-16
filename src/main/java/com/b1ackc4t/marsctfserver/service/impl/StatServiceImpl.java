package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.StatMapper;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.StatService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatServiceImpl implements StatService {
    final StatMapper statMapper;

    @Autowired
    public StatServiceImpl(StatMapper statMapper) {
        this.statMapper = statMapper;
    }

    @Override
    public ReturnRes getInfoCount() {
        Map<String, Integer> res = new HashMap<>();
        res.put("chaNum", statMapper.selectChaCount());
        res.put("learningNum", statMapper.selectLearningCount());
        res.put("userNum", statMapper.selectUserCount());
        return new ReturnRes(true, res, "查询成果");
    }

    @Override
    public ReturnRes getChaCountByType() {
        List<Map> list = statMapper.selectChaCountByType();
        Map<String, Long> res = new HashMap<>();
        for (Map chaCount : list) {
            if (((String)chaCount.get("tname")).equalsIgnoreCase("web")) {
                res.put("webNum", res.getOrDefault("webNum", 0L) + (Long)chaCount.get("num"));
            } else if (((String)chaCount.get("tname")).equalsIgnoreCase("pwn")) {
                res.put("pwnNum", res.getOrDefault("pwnNum", 0L) + (Long)chaCount.get("num"));
            } else if (((String)chaCount.get("tname")).equalsIgnoreCase("re")) {
                res.put("reNum", res.getOrDefault("reNum", 0L) + (Long)chaCount.get("num"));
            } else if (((String)chaCount.get("tname")).equalsIgnoreCase("misc")) {
                res.put("miscNum", res.getOrDefault("miscNum", 0L) + (Long)chaCount.get("num"));
            } else if (((String)chaCount.get("tname")).equalsIgnoreCase("crypto")) {
                res.put("cryptoNum", res.getOrDefault("cryptoNum", 0L) + (Long)chaCount.get("num"));
            } else {
                res.put("otherNum", res.getOrDefault("otherNum", 0L) + (Long)chaCount.get("num"));
            }
        }
        return new ReturnRes(true, res, "查询成功");
    }

    @Override
    public ReturnRes getTotalRanking(int num) {
        List<User> userList = statMapper.selectRank(num);
        int rank = 1;
        if (userList != null) {
            for (User user : userList) {
                user.setRank(rank++);
                user.setRankTitle();

            }
            return new ReturnRes(true, userList, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getRankView(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> userList = statMapper.selectRankView();
        int rank = pageSize * (pageNum - 1) + 1;
        if (userList != null) {
            for (User user : userList) {
                user.setRank(rank++);
                user.setRankTitle();

            }
            return new ReturnRes(true, new PageInfo<>(userList), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }
}
