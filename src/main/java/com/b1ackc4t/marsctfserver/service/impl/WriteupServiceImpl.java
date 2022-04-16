package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.WpCommentMapper;
import com.b1ackc4t.marsctfserver.dao.WpImageMapper;
import com.b1ackc4t.marsctfserver.dao.WriteupMapper;
import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.b1ackc4t.marsctfserver.service.WpImageService;
import com.b1ackc4t.marsctfserver.service.WriteupService;
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
public class WriteupServiceImpl extends ServiceImpl<WriteupMapper, Writeup> implements WriteupService {
    final WriteupMapper writeupMapper;
    final WpImageMapper wpImageMapper;
    final WpImageService wpImageService;
    final WpCommentMapper wpCommentMapper;

    @Autowired
    public WriteupServiceImpl(WriteupMapper writeupMapper, WpImageMapper wpImageMapper, WpImageService wpImageService, WpCommentMapper wpCommentMapper) {
        this.writeupMapper = writeupMapper;
        this.wpImageMapper = wpImageMapper;
        this.wpImageService = wpImageService;
        this.wpCommentMapper = wpCommentMapper;
    }

    @Override
    public ReturnRes saveWriteup(Writeup writeup) {
        writeup.setStatus(false);
        writeup.setLikes(0);
        writeup.setDone(false);
        writeup.setDescr(MdUtil.getDescrByMd(writeup.getText()));
        writeup.setCreTime(new Date());
        writeup.setComment("");
        if (save(writeup)) {
            wpImageMapper.updateImageInfo(writeup.getWid());
            return new ReturnRes(true, writeup.getWid(), "提交成功");
        }
        return new ReturnRes(false, "提交失败");
    }

    @Override
    public ReturnRes getWriteupByPageForUser(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Writeup> writeups = writeupMapper.selectAllForUser();
        if (writeups != null) {
            return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
        }
        return new ReturnRes(false, "查询失败");

    }

    @Override
    public ReturnRes getWriteupByWidForUser(Integer wid) {
        Writeup writeup = writeupMapper.selectByWidForUser(wid);
        if (writeup != null) return new ReturnRes(true, writeup, "查询成功");
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getUnCheckWriteupByPageForAdmin(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Writeup> writeups = writeupMapper.selectUnCheckForAdmin();
        if (writeups != null) {
            return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes passWriteup(Integer wid, Integer score, String comment) {
        Writeup writeup = new Writeup();
        writeup.setDone(true);
        writeup.setWid(wid);
        writeup.setScore(score);
        writeup.setStatus(true);
        writeup.setComment(comment);
        if (updateById(writeup)) {
            return new ReturnRes(true, writeup.getWid(), "操作成功");
        }
        return new ReturnRes(false, "操作失败");
    }

    @Override
    public ReturnRes rejectWriteup(Integer wid, Integer score, String comment) {
        Writeup writeup = new Writeup();
        writeup.setDone(true);
        writeup.setWid(wid);
        writeup.setScore(score);
        writeup.setComment(comment);
        if (updateById(writeup)) {
            return new ReturnRes(true, writeup.getWid(), "操作成功");
        }
        return new ReturnRes(false, "操作失败");
    }

    @Override
    public ReturnRes getWriteupByWidForAdmin(Integer wid) {
        Writeup writeup = writeupMapper.selectByWidForAdmin(wid);
        if (writeup != null) return new ReturnRes(true, writeup, "查询成功");
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getWriteupByPageForAdmin(int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Writeup> writeups = writeupMapper.selectAllForAdmin();
        if (writeups != null) {
            return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    /**
     * 管理员删除wp，并删除wp里的图片
     * @param writeup
     * @return
     */
    @Override
    public ReturnRes removeWriteupForAdmin(Writeup writeup) {
        wpCommentMapper.deleteByWid(writeup.getWid());
        if (removeById(writeup.getWid())) {
            wpImageService.removeWpImageByWid(writeup.getWid());
            return new ReturnRes(true, writeup.getWid(), writeup.getTitle() + " 删除成功");
        }
        return new ReturnRes(false, "操作失败");
    }

    @Override
    public ReturnRes reCheck(Integer wid, String title) {
        Writeup writeup = new Writeup();
        writeup.setDone(false);
        writeup.setWid(wid);
        writeup.setScore(0);
        writeup.setComment("");
        if (updateById(writeup)) {
            return new ReturnRes(true, writeup.getWid(), title + " 已添加到审核列表");
        }
        return new ReturnRes(false, "操作失败");
    }

    @Override
    public ReturnRes getWriteupByCidForRank(Integer cid, Integer num) {
        List<Writeup> writeups = writeupMapper.selectWriteupByCidForRank(cid, num);
        if (writeups != null) {
            return new ReturnRes(true, writeups, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getWriteupByPageForMe(Integer uid, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Writeup> writeups = writeupMapper.selectAllForMe(uid);
        if (writeups != null) {
            return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getWriteupByWidForMe(Integer uid, Integer wid) {
        if (uid.equals(writeupMapper.selectUidByWid(wid))) {
            Writeup writeup = writeupMapper.selectByWidForMe(wid);
            if (writeup != null) return new ReturnRes(true, writeup, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes removeWriteupForUser(Integer uid, Writeup writeup) {
        if (uid.equals(writeupMapper.selectUidByWid(writeup.getWid()))) {
            if (removeById(writeup.getWid())) {
                wpImageService.removeWpImageByWid(writeup.getWid());
                return new ReturnRes(true, writeup.getWid(), writeup.getTitle() + " 删除成功");
            }
        }
        return new ReturnRes(false, "操作失败");
    }

    @Override
    public ReturnRes updateWriteupForUser(Integer uid, Writeup writeup) {
        if (uid.equals(writeupMapper.selectUidByWid(writeup.getWid()))) {
            writeup.setCname(null);
            writeup.setUname(null);
            writeup.setTname(null);
            writeup.setStatus(false);
            writeup.setDone(false);
            if (writeup.getText() != null) {
                writeup.setDescr(MdUtil.getDescrByMd(writeup.getText()));
            } else {
                writeup.setDescr(null);
            }
            writeup.setCreTime(new Date());
            writeup.setComment("");
            if (updateById(writeup)) {
                wpImageMapper.updateImageInfo(writeup.getWid());
                return new ReturnRes(true, writeup.getWid(), "提交成功");
            }
        }
        return new ReturnRes(false, "提交失败");
    }

    @Override
    public ReturnRes getWriteupByUidPageForUser(Integer uid, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Writeup> writeups = writeupMapper.selectAllForUid(uid);
        if (writeups != null) {
            return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getWriteupByTypePageForUser(int pageSize, int pageNum, String tname) {
        PageHelper.startPage(pageNum, pageSize);
        List<Writeup> writeups = writeupMapper.selectAllForUserByType(tname);
        if (writeups != null) {
            return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes searchWriteupByPage(String key, String value, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        String[] whiteList = new String[]{"wid", "uname", "title", "creTime"};
        if (CommonUtil.strArrayIsHave(whiteList, key)) {
            List<Writeup> writeups = writeupMapper.selectSearchForAdmin(key, "%" + value + "%");
            if (writeups != null) {
                return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
            }
            return new ReturnRes(false, "查询失败");
        } else {
            return new ReturnRes(false, "师傅请勿尝试不安全的参数");
        }

    }

    @Override
    public ReturnRes searchWriteupByPageForUser(String value, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        List<Writeup> writeups = writeupMapper.selectAllForUserSearch("%" + value + "%");
        if (writeups != null) {
            return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes searchUnCheckWriteupByPage(String key, String value, int pageSize, int pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        String[] whiteList = new String[]{"wid", "uname", "title", "creTime"};
        if (CommonUtil.strArrayIsHave(whiteList, key)) {
            List<Writeup> writeups = writeupMapper.selectSearchUnCheckForAdmin(key, "%" + value + "%");
            if (writeups != null) {
                return new ReturnRes(true, new PageInfo<Writeup>(writeups), "查询成功");
            }
            return new ReturnRes(false, "查询失败");
        } else {
            return new ReturnRes(false, "师傅请勿尝试不安全的参数");
        }

    }
}
