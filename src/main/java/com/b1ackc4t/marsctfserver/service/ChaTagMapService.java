package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ChaTagMap;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ChaTagMapService extends IService<ChaTagMap> {
    List<ChaTagMap> getTgnameByCid(Integer cid);
    boolean removeByCid(Integer cid);

}
