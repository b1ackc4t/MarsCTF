package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WriteupService extends IService<Writeup> {
    // user级别
    ReturnRes saveWriteup(Writeup writeup);
    ReturnRes getWriteupByPageForUser(int pageSize, int pageNum);
    ReturnRes getWriteupByTypePageForUser(int pageSize, int pageNum, String tname);
    ReturnRes getWriteupByUidPageForUser(Integer uid, int pageSize, int pageNum);
    ReturnRes getWriteupByWidForUser(Integer wid);
    ReturnRes getWriteupByCidForRank(Integer cid, Integer num);
    ReturnRes getWriteupByPageForMe(Integer uid, int pageSize, int pageNum);
    ReturnRes getWriteupByWidForMe(Integer uid, Integer wid);
    ReturnRes removeWriteupForUser(Integer uid, Writeup writeup);
    ReturnRes updateWriteupForUser(Integer uid, Writeup writeup);
    ReturnRes searchWriteupByPageForUser(String value, int pageSize, int pageNum);

    // admin级别
    ReturnRes getUnCheckWriteupByPageForAdmin(int pageSize, int pageNum);
    ReturnRes getWriteupByPageForAdmin(int pageSize, int pageNum);
    ReturnRes passWriteup(Integer wid, Integer score, String comment);
    ReturnRes rejectWriteup(Integer wid, Integer score, String comment);
    ReturnRes getWriteupByWidForAdmin(Integer wid);
    ReturnRes removeWriteupForAdmin(Writeup writeup);
    ReturnRes reCheck(Integer wid, String title);
    ReturnRes searchWriteupByPage(String key, String value, int pageSize, int pageNum);
    ReturnRes searchUnCheckWriteupByPage(String key, String value, int pageSize, int pageNum);
}
