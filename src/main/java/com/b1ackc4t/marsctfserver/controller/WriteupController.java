package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
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
        writeup.setUid(((MyUserDetails)authentication.getPrincipal()).getUid());
        if (action.equals("save")) {
            return writeupService.saveWriteup(writeup);
        } if (action.equals("remove")) {
            return null;
        } if (action.equals("update")) {
            return null;
        } else {
            return new ReturnRes(false, "action value error");
        }
    }

    @GetMapping("/user/writeup/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryByPageForUser(@PathVariable int pageSize,
                                        @PathVariable int pageNum) {
        return writeupService.getWriteupByPageForUser(pageSize, pageNum);
    }

    @GetMapping("/user/writeup/{wid:\\d+}")
    public ReturnRes queryByWidForUser(@PathVariable int wid) {
        return writeupService.getWriteupByWid(wid);
    }

    @GetMapping("/admin/writeup/uncheck/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes queryUnCheckByPageForAdmin(@PathVariable int pageSize,
                                        @PathVariable int pageNum) {
        return writeupService.getUnCheckWriteupByPageForAdmin(pageSize, pageNum);
    }

    @PostMapping("/admin/writeup/uncheck/passWriteup")
    public ReturnRes passWriteup(@RequestParam Integer wid,
                                 @RequestParam Integer score) {
        return writeupService.passWriteup(wid, score);
    }
}
