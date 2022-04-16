package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.LearnCommentMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaComment;
import com.b1ackc4t.marsctfserver.pojo.LearnComment;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.LearnCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LearnCommentServiceImpl extends ServiceImpl<LearnCommentMapper, LearnComment> implements LearnCommentService {
    final LearnCommentMapper learnCommentMapper;

    public LearnCommentServiceImpl(LearnCommentMapper learnCommentMapper) {
        this.learnCommentMapper = learnCommentMapper;
    }

    @Override
    public ReturnRes saveComment(LearnComment learnComment) {
        learnComment.setLcid(null);
        learnComment.setCreateTime(new Date());
        if (save(learnComment)) {
            return new ReturnRes(true, "评论成功");
        }
        return new ReturnRes(false, "评论失败");
    }

    @Override
    public ReturnRes removeComment(Integer lcid) {
        if (removeById(lcid)) {
            return new ReturnRes(true, "删除成功");
        }
        return new ReturnRes(false, "删除失败");
    }

    @Override
    public ReturnRes getCommentPageByLid(Integer lid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<LearnComment> learnComments = learnCommentMapper.selectCommentByLid(lid);
        if (learnComments != null) {
            return new ReturnRes(true, new PageInfo<>(learnComments), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }
}
