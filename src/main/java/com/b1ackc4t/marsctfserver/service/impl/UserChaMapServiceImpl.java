package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.UserChaMapMapper;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.UserChaMap;
import com.b1ackc4t.marsctfserver.service.UserChaMapService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserChaMapServiceImpl extends ServiceImpl<UserChaMapMapper, UserChaMap> implements UserChaMapService {
    final UserChaMapMapper userChaMapMapper;

    @Autowired
    public UserChaMapServiceImpl(UserChaMapMapper userChaMapMapper) {
        this.userChaMapMapper = userChaMapMapper;
    }

    @Override
    public String getStatusById(Integer uid, Integer cid) {
        return userChaMapMapper.selectStatus(uid, cid);
    }

    @Override
    public boolean updateById(UserChaMap userChaMap) {
        if (userChaMap.getRank() == null) {
            return userChaMapMapper.updateById2(userChaMap) == 1;
        }
        return userChaMapMapper.updateById1(userChaMap) == 1;
    }

    @Override
    public void removeByCid(Integer cid) {
        userChaMapMapper.deleteByCid(cid);
    }

    @Override
    public void removeByUid(Integer uid) {
        userChaMapMapper.deleteByUid(uid);
    }

    @Override
    public ReturnRes getChaStatusForUser(Integer uid) {
        List<UserChaMap> userChaMaps = userChaMapMapper.selectChaStatusForUser(uid);
        if (userChaMaps != null) {
            return new ReturnRes(true, userChaMaps, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }
}
