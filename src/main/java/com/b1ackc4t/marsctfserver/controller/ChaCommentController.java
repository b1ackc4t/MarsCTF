package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.dao.ChaCommentMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaComment;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class ChaCommentController {
    final ChaCommentService chaCommentService;
    final ChaCommentMapper chaCommentMapper;

    @Autowired
    public ChaCommentController(ChaCommentService chaCommentService, ChaCommentMapper chaCommentMapper) {
        this.chaCommentService = chaCommentService;
        this.chaCommentMapper = chaCommentMapper;
    }

    @PostMapping("/user/chaComment/save")
    public ReturnRes saveComment(@RequestParam(name = "data") String commentJson,
                                 Authentication authentication) {
        ChaComment chaComment = JSONObject.parseObject(commentJson, ChaComment.class);
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        chaComment.setUid(uid);
        return chaCommentService.saveComment(chaComment);
    }

    @PostMapping("/user/chaComment/remove")
    public ReturnRes removeComment(@RequestParam Integer ccid,
                                 Authentication authentication) {
        SecurityExpressionRoot securityExpressionRoot = new SecurityExpressionRoot(authentication) {};
        if (!securityExpressionRoot.hasRole("admin")) {
            Integer commentUid = chaCommentMapper.selectUidByCcid(ccid);
            Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
            if (!uid.equals(commentUid)) {
                return new ReturnRes(false, "抱歉，您权限不足");
            }
        }
        return chaCommentService.removeComment(ccid);
    }

    @GetMapping("/user/chaComment/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getComment(@RequestParam Integer cid,
                                @PathVariable int pageSize,
                                @PathVariable int pageNum) {

        return chaCommentService.getCommentPageByCid(cid, pageNum, pageSize);
    }
}
