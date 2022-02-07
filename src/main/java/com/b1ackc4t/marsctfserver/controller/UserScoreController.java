package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.UserScoreService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserScoreController {

    final UserScoreService userScoreService;

    public UserScoreController(UserScoreService userScoreService) {
        this.userScoreService = userScoreService;
    }

    @GetMapping("/userScore/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getScoreByPage(@PathVariable int pageSize,
                                    @PathVariable int pageNum) {
        PageInfo<User> pageInfo = userScoreService.getUserScoreByPage(pageNum, pageSize);
        if (pageInfo != null) {
            return new ReturnRes(true, pageInfo, "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }
    }

    @GetMapping("/userScore/challengeRank/{cid:\\d+}")
    public ReturnRes getRankForChallenge(@PathVariable Integer cid) {
        return userScoreService.getChaRanking(cid, 10);
    }
}
