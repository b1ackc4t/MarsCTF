package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.ChallengeService;
import com.b1ackc4t.marsctfserver.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChallengeController {

    final UserService userService;
    final ChallengeService challengeService;

    @Autowired
    public ChallengeController(UserService userService, ChallengeService challengeService) {
        this.userService = userService;
        this.challengeService = challengeService;
    }

    @GetMapping("/challenge/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryForAllByPage(@PathVariable int pageSize,
                                       @PathVariable int pageNum,
                                       Authentication authentication,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) String tag) {
        if (authentication != null) {
            SecurityExpressionRoot securityExpressionRoot = new SecurityExpressionRoot(authentication) {};
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            if (type != null) {

                return challengeService.getForUserByPageByType(userDetails.getUid(), pageSize, pageNum, type);
            } else if (tag != null){
                return challengeService.getForUserByPageByTag(userDetails.getUid(), pageSize, pageNum, tag);
            } else {
                return challengeService.getForUserByPage(userDetails.getUid(), pageSize, pageNum);
            }
        } else {
            if (type != null) {
                return challengeService.getForAllByPageByType(pageSize, pageNum, type);
            } else if (tag != null){
                return challengeService.getForAllByPageByTag(pageSize, pageNum, tag);
            } else {
                return challengeService.getForAllByPage(pageSize, pageNum);
            }

        }

    }

    @PostMapping("/admin/challenge")
    public ReturnRes operate(@RequestParam String action,
                             @RequestParam(name = "data") String challengeJson,
                             Authentication authentication) {
        Challenge challenge = JSONObject.parseObject(challengeJson, Challenge.class);
        if (action.equals("save")) {
            MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
            challenge.setMasterUid(myUserDetails.getUid()); // 记录题目创始人
            challenge.setMasterName(myUserDetails.getUsername());
            if (challengeService.save(challenge)) {
                return new ReturnRes(true, challenge, String.format("%s 添加成功", challenge.getCname()));
            } else {
                return new ReturnRes(false, String.format("%s 添加失败，可能是题目名称重复", challenge.getCname()));
            }
        } else if (action.equals("update")) {
            if (challengeService.update(challenge)) {
                return new ReturnRes(true, challenge, String.format("%s 修改成功", challenge.getCname()));
            } else {
                return new ReturnRes(false, String.format("%s 修改失败，可能是题目名称重复", challenge.getCname()));
            }
        } else if (action.equals("remove")) {
            if (challengeService.remove(challenge.getCid())) {
                return new ReturnRes(true, String.format("%s 删除成功", challenge.getCname()));
            } else {
                return new ReturnRes(false, String.format("%s 删除失败", challenge.getCname()));
            }
        } else {
            return new ReturnRes(false, "action error");
        }
    }

    @GetMapping("/admin/challenge/{cid:\\d+}")
    public ReturnRes queryForAdmin(@PathVariable int cid) {
        Challenge challenge = challengeService.getByIdForAdmin(cid);
        if (challenge != null) {
            return new ReturnRes(true, challenge, "查询成功");
        } else {
            return new ReturnRes(false, "查询失败");
        }

    }

    /**
     * 分页查询题目信息
     * @param pageSize
     * @param pageNum
     * @return
     */
    @GetMapping("/admin/challenge/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPage(@PathVariable int pageSize,
                                 @PathVariable int pageNum) {
        PageInfo<Challenge> pageInfo = challengeService.getAllChallengeByPage(pageNum, pageSize);
        if (pageInfo != null) {
            return new ReturnRes(true, pageInfo);
        }
        return new ReturnRes(false);
    }

    @PostMapping("/user/challenge/submitFlag")
    public ReturnRes submitFlag(@RequestParam Integer cid,
                                @RequestParam String flag,
                                Authentication authentication) {
        SecurityExpressionRoot securityExpressionRoot = new SecurityExpressionRoot(authentication) {};
        String username = authentication.getName();
        User user = userService.getByUserName(username);
        return challengeService.submitFlag(user, cid, flag);
    }

    @GetMapping("/user/challenge/{id:\\d+}")
    public ReturnRes queryForUser(@PathVariable Integer id) {
        return challengeService.getByIdForUser(id);
    }

    @GetMapping("/challenge/simple")
    public ReturnRes querySimple() {
        return challengeService.getSimpleAll();
    }

    @GetMapping("/admin/challenge/search/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPage(@PathVariable int pageSize,
                                 @PathVariable int pageNum,
                                 @RequestParam String key,
                                 @RequestParam String value) {
        return challengeService.searchChallengeByPage(key, value, pageNum, pageSize);
    }
}
