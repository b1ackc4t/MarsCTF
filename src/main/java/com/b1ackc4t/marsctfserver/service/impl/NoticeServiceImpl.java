package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.NoticeImageMapper;
import com.b1ackc4t.marsctfserver.dao.NoticeMapper;
import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.Notice;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.NoticeImageService;
import com.b1ackc4t.marsctfserver.service.NoticeService;
import com.b1ackc4t.marsctfserver.util.CommonUtil;
import com.b1ackc4t.marsctfserver.util.MdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements NoticeService {
    final NoticeMapper noticeMapper;
    final NoticeImageMapper noticeImageMapper;
    final NoticeImageService noticeImageService;

    @Autowired
    public NoticeServiceImpl(NoticeMapper noticeMapper, NoticeImageMapper noticeImageMapper, NoticeImageService noticeImageService) {
        this.noticeMapper = noticeMapper;
        this.noticeImageMapper = noticeImageMapper;
        this.noticeImageService = noticeImageService;
    }

    @Override
    public ReturnRes saveNotice(Notice notice) {
        notice.setNid(null);
        notice.setCreateTime(new Date());
        notice.setDescr(MdUtil.getDescrByMd(notice.getText()));
        if (save(notice)) {
            noticeImageMapper.updateImageInfo(notice.getNid());
            return new ReturnRes(true, "添加成功");
        }
        return new ReturnRes(false, "添加失败");
    }

    @Override
    public ReturnRes removeNotice(Notice notice) {
        if (removeById(notice.getNid())) {
            noticeImageService.removeImageByNid(notice.getNid());
            return new ReturnRes(true, notice.getTitle() + " 删除成功");
        }
        return new ReturnRes(false, "删除失败");
    }

    @Override
    public ReturnRes updateNotice(Notice notice) {
        notice.setCreateTime(null);
        notice.setUid(null);
        notice.setUname(null);
        if (notice.getText() != null) {
            notice.setDescr(MdUtil.getDescrByMd(notice.getText()));
        } else {
            notice.setDescr(null);
        }
        if (updateById(notice)) {
            noticeImageMapper.updateImageInfo(notice.getNid());
            return new ReturnRes(true, "更新成功");
        }
        return new ReturnRes(false, "更新失败");
    }

    @Override
    public ReturnRes getNoticePageForAdmin(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeMapper.selectNoticeByPageForAdmin();
        if (notices != null) {
            return new ReturnRes(true, new PageInfo<>(notices), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getNoticeByIdForAdmin(Integer nid) {
        Notice notice = noticeMapper.selectNoticeByIdForAdmin(nid);
        if (notice != null) {
            return new ReturnRes(true, notice, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getNoticePage(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Notice> notices = noticeMapper.selectNoticeByPage();
        if (notices != null) {
            return new ReturnRes(true, new PageInfo<>(notices), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getNoticeByIdForUser(Integer nid) {
        Notice notice = noticeMapper.selectNoticeByIdForAdmin(nid);
        if (notice != null) {
            return new ReturnRes(true, notice, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes searchNoticeForAdmin(String key, String value, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        String[] whiteList = new String[]{"nid", "title", "createTime", "uname"};
        if (CommonUtil.strArrayIsHave(whiteList, key)) {
            List<Notice> notices = noticeMapper.selectSearchNoticeByPageForAdmin(key, "%" + value + "%");
            if (notices != null) {
                return new ReturnRes(true, new PageInfo<>(notices), "查询成功");
            }
            return new ReturnRes(false, "查询失败");
        } else {
            return new ReturnRes(false, "师傅请勿尝试不安全的参数");
        }

    }
}
