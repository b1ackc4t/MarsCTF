package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChaCommentMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaComment;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ChaCommentServiceImpl extends ServiceImpl<ChaCommentMapper, ChaComment> implements ChaCommentService {
    final ChaCommentMapper chaCommentMapper;

    @Autowired
    public ChaCommentServiceImpl(ChaCommentMapper chaCommentMapper) {
        this.chaCommentMapper = chaCommentMapper;
    }

    @Override
    public ReturnRes saveComment(ChaComment chaComment) {
        chaComment.setCcid(null);
        chaComment.setCreateTime(new Date());
        if (save(chaComment)) {
            return new ReturnRes(true, "评论成功");
        }
        return new ReturnRes(false, "评论失败");
    }

    @Override
    public ReturnRes removeComment(Integer ccid) {
        if (removeById(ccid)) {
            return new ReturnRes(true, "删除成功");
        }
        return new ReturnRes(false, "删除失败");
    }

    @Override
    public ReturnRes getCommentPageByCid(Integer cid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ChaComment> chaComments = chaCommentMapper.selectCommentByCid(cid);
        if (chaComments != null) {
            return new ReturnRes(true, new PageInfo<>(chaComments), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }
}
