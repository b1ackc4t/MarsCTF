package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.WriteupMapper;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.b1ackc4t.marsctfserver.service.WriteupService;
import com.b1ackc4t.marsctfserver.util.MdUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class WriteupServiceImpl extends ServiceImpl<WriteupMapper, Writeup> implements WriteupService {
    final WriteupMapper writeupMapper;

    public WriteupServiceImpl(WriteupMapper writeupMapper) {
        this.writeupMapper = writeupMapper;
    }

    @Override
    public ReturnRes saveWriteup(Writeup writeup) {
        writeup.setStatus(false);
        writeup.setLikes(0);
        writeup.setDone(false);
        writeup.setDescr(MdUtil.getDescrByMd(writeup.getText()));
        writeup.setCreTime(new Date());
        if (save(writeup)) {
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
    public ReturnRes getWriteupByWid(Integer wid) {
        Writeup writeup = writeupMapper.selectByWid(wid);
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
    public ReturnRes passWriteup(Integer wid, Integer score) {
        Writeup writeup = new Writeup();
        writeup.setDone(true);
        writeup.setWid(wid);
        writeup.setScore(score);
        writeup.setStatus(true);
        if (updateById(writeup)) {
            return new ReturnRes(true, writeup.getWid(), "操作成功");
        }
        return new ReturnRes(false, "操作失败");
    }
}
