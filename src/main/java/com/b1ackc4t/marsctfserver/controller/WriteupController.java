package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.b1ackc4t.marsctfserver.service.WriteupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class WriteupController {
    final WriteupService writeupService;

    @Autowired
    public WriteupController(WriteupService writeupService) {
        this.writeupService = writeupService;
    }

    @PostMapping("/user/writeup")
    public ReturnRes operate(@RequestParam String action,
                             @RequestParam(name = "data") String writeupJson,
                             Authentication authentication) {
        Writeup writeup = JSONObject.parseObject(writeupJson, Writeup.class);
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        writeup.setUid(uid);
        if (action.equals("save")) {
            return writeupService.saveWriteup(writeup);
        } if (action.equals("remove")) {
            return writeupService.removeWriteupForUser(uid, writeup);
        } if (action.equals("update")) {
            return writeupService.updateWriteupForUser(uid, writeup);
        } else {
            return new ReturnRes(false, "action value error");
        }
    }

    @GetMapping("/user/writeup/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPageForUser(@PathVariable int pageSize,
                                        @PathVariable int pageNum,
                                        @RequestParam(required = false) String type) {
        if (type == null) {
            return writeupService.getWriteupByPageForUser(pageSize, pageNum);
        } else {
            return writeupService.getWriteupByTypePageForUser(pageSize, pageNum, type);
        }

    }

    @GetMapping("/user/writeup/{wid:\\d+}")
    public ReturnRes queryByWidForUser(@PathVariable int wid) {
        return writeupService.getWriteupByWidForUser(wid);
    }

    @GetMapping("/admin/writeup/uncheck/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryUnCheckByPageForAdmin(@PathVariable int pageSize,
                                                @PathVariable int pageNum) {
        return writeupService.getUnCheckWriteupByPageForAdmin(pageSize, pageNum);
    }

    @PostMapping("/admin/writeup/uncheck/passWriteup")
    public ReturnRes passWriteup(@RequestParam Integer wid,
                                 @RequestParam Integer score,
                                 @RequestParam String comment) {
        return writeupService.passWriteup(wid, score, comment);
    }

    @PostMapping("/admin/writeup/uncheck/rejectWriteup")
    public ReturnRes rejectWriteup(@RequestParam Integer wid,
                                 @RequestParam Integer score,
                                   @RequestParam String comment) {
        return writeupService.rejectWriteup(wid, score, comment);
    }

    @GetMapping("/admin/writeup/{wid:\\d+}")
    public ReturnRes queryByWidForAdmin(@PathVariable int wid) {
        return writeupService.getWriteupByWidForAdmin(wid);
    }

    @GetMapping("/admin/writeup/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPageForAdmin(@PathVariable int pageSize,
                                                @PathVariable int pageNum) {
        return writeupService.getWriteupByPageForAdmin(pageSize, pageNum);
    }

    @PostMapping("/admin/writeup")
    public ReturnRes operate(@RequestParam String action,
                             @RequestParam(name = "data") String writeupJson) {
        Writeup writeup = JSONObject.parseObject(writeupJson, Writeup.class);
        if (action.equals("remove")) {
            return writeupService.removeWriteupForAdmin(writeup);
        } else {
            return new ReturnRes(false, "操作暂不支持");
        }
    }

    @PostMapping("/admin/writeup/reCheck")
    public ReturnRes reCheck(@RequestParam int wid,
                             @RequestParam String title) {
        return writeupService.reCheck(wid, title);
    }

    @GetMapping("/user/writeupRank/{cid:\\d+}")
    public ReturnRes getWriteupRankByCid(@PathVariable Integer cid) {
        return writeupService.getWriteupByCidForRank(cid, 10);
    }

    @GetMapping("/user/writeup/getMe/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getWriteupByPageForMe(@PathVariable int pageSize,
                                           @PathVariable int pageNum,
                                           Authentication authentication) {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        return writeupService.getWriteupByPageForMe(uid, pageSize, pageNum);
    }

    @GetMapping("/user/writeup/getMe/{wid:\\d+}")
    public ReturnRes getWriteupByWidForMe(@PathVariable Integer wid,
                                          Authentication authentication) {
        Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
        return writeupService.getWriteupByWidForMe(uid, wid);
    }

    @GetMapping("/user/writeup/{uid:\\d+}/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getWriteupByPageForUid(@PathVariable int pageSize,
                                           @PathVariable int pageNum,
                                           @PathVariable Integer uid) {
        return writeupService.getWriteupByUidPageForUser(uid, pageSize, pageNum);
    }

    @GetMapping("/user/writeup/search/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes searchWriteupByPageForUser(@PathVariable int pageSize,
                                            @PathVariable int pageNum,
                                            @RequestParam String value) {
        return writeupService.searchWriteupByPageForUser(value, pageSize, pageNum);
    }

    @GetMapping("/admin/writeup/search/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPageForAdmin(@PathVariable int pageSize,
                                         @PathVariable int pageNum,
                                         @RequestParam String key,
                                         @RequestParam String value) {
        return writeupService.searchWriteupByPage(key, value, pageSize, pageNum);
    }

    @GetMapping("/admin/writeup/uncheck/search/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryUnCheckByPageForAdmin(@PathVariable int pageSize,
                                         @PathVariable int pageNum,
                                         @RequestParam String key,
                                         @RequestParam String value) {
        return writeupService.searchUnCheckWriteupByPage(key, value, pageSize, pageNum);
    }

}
