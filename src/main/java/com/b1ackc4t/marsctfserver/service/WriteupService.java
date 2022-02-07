package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WriteupService extends IService<Writeup> {
    ReturnRes saveWriteup(Writeup writeup);
    ReturnRes getWriteupByPageForUser(int pageSize, int pageNum);
    ReturnRes getWriteupByWid(Integer wid);
    ReturnRes getUnCheckWriteupByPageForAdmin(int pageSize, int pageNum);
    ReturnRes passWriteup(Integer wid, Integer score);
}
