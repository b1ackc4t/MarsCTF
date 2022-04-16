package com.b1ackc4t.marsctfserver.controller;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.UserChaMapService;
import com.b1ackc4t.marsctfserver.service.UserScoreService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserScoreController {

    final UserScoreService userScoreService;
    final UserChaMapService userChaMapService;

    @Autowired
    public UserScoreController(UserScoreService userScoreService, UserChaMapService userChaMapService) {
        this.userScoreService = userScoreService;
        this.userChaMapService = userChaMapService;
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

    @GetMapping("/user/userScore/scoreChart/{uid:\\d+}")
    public ReturnRes getScoreChart(@PathVariable Integer uid) {
        return userScoreService.getScoreChart(uid);
    }

    @GetMapping("/user/userScore/challengeStatus/{uid:\\d+}")
    public ReturnRes getChallengeStatus(@PathVariable Integer uid) {
        return userChaMapService.getChaStatusForUser(uid);
    }

    @GetMapping("/userScore/search/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getScoreByPage(@PathVariable int pageSize,
                                    @PathVariable int pageNum,
                                    @RequestParam String key,
                                    @RequestParam String value) {
        return userScoreService.searchUserScore(key, value, pageNum, pageSize);
    }
}
