package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.dao.WpCommentMapper;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.WpComment;
import com.b1ackc4t.marsctfserver.service.WpCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WpCommentController {
    final WpCommentService wpCommentService;
    final WpCommentMapper wpCommentMapper;

    @Autowired
    public WpCommentController(WpCommentService wpCommentService, WpCommentMapper wpCommentMapper) {
        this.wpCommentService = wpCommentService;
        this.wpCommentMapper = wpCommentMapper;
    }

    @PostMapping("/user/wpComment/save")
    public ReturnRes saveComment(@RequestParam(name = "data") String wpJson,
                                 Authentication authentication) {
        WpComment wpComment = JSONObject.parseObject(wpJson, WpComment.class);
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        wpComment.setUid(uid);
        return wpCommentService.saveComment(wpComment);
    }

    @PostMapping("/user/wpComment/remove")
    public ReturnRes removeComment(@RequestParam Integer wcid,
                                   Authentication authentication) {
        SecurityExpressionRoot securityExpressionRoot = new SecurityExpressionRoot(authentication) {};
        if (!securityExpressionRoot.hasRole("admin")) {
            Integer commentUid = wpCommentMapper.selectUidByWcid(wcid);
            Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
            if (!uid.equals(commentUid)) {
                return new ReturnRes(false, "抱歉，您权限不足");
            }
        }
        return wpCommentService.removeComment(wcid);
    }

    @GetMapping("/user/wpComment/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getComment(@RequestParam Integer wid,
                                @PathVariable int pageSize,
                                @PathVariable int pageNum) {

        return wpCommentService.getCommentPageByWid(wid, pageNum, pageSize);
    }
}
