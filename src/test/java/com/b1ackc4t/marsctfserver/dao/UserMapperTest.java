package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.util.generator.ShortUUID;
import com.b1ackc4t.marsctfserver.util.generator.SnowFlakeUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 查询单个用户
     */
    @Test
    void fun1() {
        User user = userMapper.selectById(11);
        System.out.println(user);
    }

    @Test
    void fun2() {
        User user = new User();
        user.setUid(ShortUUID.generateUid());
        user.setUname("admin");
        user.setEmail("11111@qq.com");

        user.setUpassword(new BCryptPasswordEncoder().encode("123456"));
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        System.out.format(sdf.format(date));
        user.setRegTime(date);
        userMapper.insert(user);
    }

    @Test
    void fun3() {
        User user = userMapper.selectByUserNameAndPassword("xsh2", "123456");
        System.out.format(user.getEmail());
    }

    @Test
    void fun4() {
        IPage iPage = userMapper.selectPage(new Page<>(2,5), null);
        iPage.getRecords();
    }

}