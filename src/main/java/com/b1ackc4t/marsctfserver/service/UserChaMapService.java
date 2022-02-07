package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.UserChaMap;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserChaMapService extends IService<UserChaMap> {
    String getStatusById(Integer uid, Integer cid);
    boolean updateById(UserChaMap userChaMap);
    void removeByCid(Integer cid);
    void removeByUid(Integer uid);
}
