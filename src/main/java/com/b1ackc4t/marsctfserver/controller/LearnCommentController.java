package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.dao.LearnCommentMapper;
import com.b1ackc4t.marsctfserver.pojo.LearnComment;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.LearnCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LearnCommentController {
    final LearnCommentMapper learnCommentMapper;
    final LearnCommentService learnCommentService;

    @Autowired
    public LearnCommentController(LearnCommentMapper learnCommentMapper, LearnCommentService learnCommentService) {
        this.learnCommentMapper = learnCommentMapper;
        this.learnCommentService = learnCommentService;
    }

    @PostMapping("/user/learnComment/save")
    public ReturnRes saveComment(@RequestParam(name = "data") String commentJson,
                                 Authentication authentication) {
        LearnComment learnComment = JSONObject.parseObject(commentJson, LearnComment.class);
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        learnComment.setUid(uid);
        return learnCommentService.saveComment(learnComment);
    }

    @PostMapping("/user/learnComment/remove")
    public ReturnRes removeComment(@RequestParam Integer lcid,
                                   Authentication authentication) {
        SecurityExpressionRoot securityExpressionRoot = new SecurityExpressionRoot(authentication) {};
        if (!securityExpressionRoot.hasRole("admin")) {
            Integer commentUid = learnCommentMapper.selectUidByLcid(lcid);
            Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
            if (!uid.equals(commentUid)) {
                return new ReturnRes(false, "抱歉，您权限不足");
            }
        }
        return learnCommentService.removeComment(lcid);
    }

    @GetMapping("/user/learnComment/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getComment(@RequestParam Integer lid,
                                @PathVariable int pageSize,
                                @PathVariable int pageNum) {

        return learnCommentService.getCommentPageByLid(lid, pageNum, pageSize);
    }
}
