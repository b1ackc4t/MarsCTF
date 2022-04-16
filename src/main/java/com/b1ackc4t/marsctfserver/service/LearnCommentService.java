package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.LearnComment;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LearnCommentService extends IService<LearnComment> {
    ReturnRes saveComment(LearnComment learnComment);
    ReturnRes removeComment(Integer lcid);
    ReturnRes getCommentPageByLid(Integer lid, int pageNum, int pageSize);
}
