package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.Notice;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoticeService extends IService<Notice> {
    ReturnRes saveNotice(Notice notice);
    ReturnRes removeNotice(Notice notice);
    ReturnRes updateNotice(Notice notice);
    ReturnRes getNoticePageForAdmin(int pageSize, int pageNum);
    ReturnRes getNoticeByIdForAdmin(Integer nid);
    ReturnRes getNoticePage(int pageSize, int pageNum);
    ReturnRes getNoticeByIdForUser(Integer nid);
    ReturnRes searchNoticeForAdmin(String key, String value, int pageSize, int pageNum);
}
