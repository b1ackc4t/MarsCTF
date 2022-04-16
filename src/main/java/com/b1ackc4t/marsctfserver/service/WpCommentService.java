package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.WpComment;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WpCommentService extends IService<WpComment> {
    ReturnRes saveComment(WpComment wpComment);
    ReturnRes removeComment(Integer wcid);
    ReturnRes getCommentPageByWid(Integer wid, int pageNum, int pageSize);
}
