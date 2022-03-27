package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.Learning;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.LearningService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LearningController {
    final LearningService learningService;

    public LearningController(LearningService learningService) {
        this.learningService = learningService;
    }

    @PostMapping("/admin/learning")
    public ReturnRes operate(@RequestParam String action,
                             @RequestParam(name = "data") String learningJson,
                             Authentication authentication) {
        Learning learning = JSONObject.parseObject(learningJson, Learning.class);
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        learning.setUid(uid);
        if (action.equals("save")) {
            return learningService.saveLearning(learning);
        } else if (action.equals("update")) {
            return learningService.updateLearning(learning);
        } else if (action.equals("remove")) {
            return learningService.removeLearning(learning);
        } else {
            return new ReturnRes(false, "操作暂不支持");
        }
    }

    @GetMapping("/admin/learning/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getLearningByPageForAdmin(@PathVariable int pageSize,
                                               @PathVariable int pageNum) {
        return learningService.getLearningByPageForAdmin(pageSize, pageNum);
    }

    @GetMapping("/admin/learning/{lid:\\d+}")
    public ReturnRes getLearningByLidForAdminEdit(@PathVariable int lid) {
        return learningService.getLearningByLidForAdminEdit(lid);
    }

    @GetMapping("/user/learning/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getLearningByPageForUser(@PathVariable int pageSize,
                                              @PathVariable int pageNum,
                                              @RequestParam(required = false) String type,
                                              @RequestParam(required = false) String tag) {
        if (tag != null) return learningService.getLearningByTagForUser(pageSize, pageNum, tag);
        if (type == null) {
            return learningService.getLearningByPageForUser(pageSize, pageNum);
        } else {
            return learningService.getLearningByTypeForUser(pageSize, pageNum, type);
        }

    }

    @GetMapping("/user/learning/{lid:\\d+}")
    public ReturnRes getLearningByLidForUser(@PathVariable int lid) {
        return learningService.getLearningByLidForUser(lid);
    }

    @GetMapping("/user/learning/getChallenge/{lid:\\d+}")
    public ReturnRes getChallengeByLidForUser(@PathVariable int lid,
                                              Authentication authentication) {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        return learningService.getChallengeByLidForUser(uid, lid);
    }
}
