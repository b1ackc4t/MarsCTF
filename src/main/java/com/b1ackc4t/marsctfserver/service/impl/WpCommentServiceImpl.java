package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.WpCommentMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaComment;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.WpComment;
import com.b1ackc4t.marsctfserver.service.WpCommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WpCommentServiceImpl extends ServiceImpl<WpCommentMapper, WpComment> implements WpCommentService {
    final WpCommentMapper wpCommentMapper;

    @Autowired
    public WpCommentServiceImpl(WpCommentMapper wpCommentMapper) {
        this.wpCommentMapper = wpCommentMapper;
    }

    @Override
    public ReturnRes saveComment(WpComment wpComment) {
        wpComment.setWcid(null);
        wpComment.setCreateTime(new Date());
        if (save(wpComment)) {
            return new ReturnRes(true, "评论成功");
        }
        return new ReturnRes(false, "评论失败");
    }

    @Override
    public ReturnRes removeComment(Integer wcid) {
        if (removeById(wcid)) {
            return new ReturnRes(true, "删除成功");
        }
        return new ReturnRes(false, "删除失败");
    }

    @Override
    public ReturnRes getCommentPageByWid(Integer wid, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<WpComment> wpComments = wpCommentMapper.selectCommentByWid(wid);
        if (wpComments != null) {
            return new ReturnRes(true, new PageInfo<>(wpComments), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }
}
