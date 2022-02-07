package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaRankPojo;
import com.b1ackc4t.marsctfserver.pojo.UserChaMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserChaMapMapperTest {

    @Autowired
    UserChaMapMapper userChaMapMapper;

    @Test
    void fun01() {
        System.out.println(userChaMapMapper.selectStatus(105355682, 37));
        System.out.println();
    }

    @Test
    void fun02() {
        UserChaMap userChaMap = new UserChaMap();
        userChaMap.setUid(105355682);
        userChaMap.setCid(36);
        userChaMap.setStatus("fail");
        userChaMap.setFinishTime(new Date());
        userChaMap.setRank(1);
        System.out.println(userChaMapMapper.updateById1(userChaMap));
        System.out.println();
    }

    @Test
    void fun03() {

    }
}