package com.b1ackc4t.marsctfserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.config.security.MyUserDetails;
import com.b1ackc4t.marsctfserver.pojo.Notice;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class NoticeController {
    final NoticeService noticeService;

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    @PostMapping("/admin/notice")
    public ReturnRes operate(@RequestParam(name = "data") String noticeJson,
                             @RequestParam String action,
                             Authentication authentication) {
        Notice notice = JSONObject.parseObject(noticeJson, Notice.class);
        if (action.equals("save")) {
            Integer uid = ((MyUserDetails)authentication.getPrincipal()).getUid();
            notice.setUid(uid);
            return noticeService.saveNotice(notice);
        } else if (action.equals("update")) {
            return noticeService.updateNotice(notice);
        } else if (action.equals("remove")) {
            return noticeService.removeNotice(notice);
        } else {
            return new ReturnRes(false, "操作不支持");
        }
    }

    @GetMapping("/admin/notice/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getNoticePageForAdmin(@PathVariable int pageSize,
                                   @PathVariable int pageNum) {
        return noticeService.getNoticePageForAdmin(pageSize, pageNum);
    }

    @GetMapping("/admin/notice/{nid:\\d+}")
    public ReturnRes getNoticeByIdForAdmin(@PathVariable Integer nid) {
        return noticeService.getNoticeByIdForAdmin(nid);
    }

    @GetMapping("/notice/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getNoticePage(@PathVariable int pageSize,
                                   @PathVariable int pageNum) {
        return noticeService.getNoticePage(pageSize, pageNum);
    }

    @GetMapping("/user/notice/{nid:\\d+}")
    public ReturnRes getNoticeByIdForUser(@PathVariable Integer nid) {
        return noticeService.getNoticeByIdForUser(nid);
    }

    @GetMapping("/admin/notice/search/{pageSize:\\d+}/{pageNum:\\d+}")
    public ReturnRes getNoticePageForAdmin(@PathVariable int pageSize,
                                           @PathVariable int pageNum,
                                           @RequestParam String key,
                                           @RequestParam String value) {
        return noticeService.searchNoticeForAdmin(key, value, pageSize, pageNum);
    }


}
