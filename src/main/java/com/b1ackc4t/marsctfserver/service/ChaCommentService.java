package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ChaComment;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ChaCommentService extends IService<ChaComment> {
    ReturnRes saveComment(ChaComment chaComment);
    ReturnRes removeComment(Integer ccid);
    ReturnRes getCommentPageByCid(Integer cid, int pageNum, int pageSize);
}
