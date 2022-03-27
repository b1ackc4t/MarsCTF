package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.UserMapper;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    final UserMapper userMapper;

    @Override
    public User getByUserName(String username) {
        return userMapper.selectByUserName(username);
    }

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {

        this.userMapper = userMapper;
    }

    @Override
    public boolean usernameIsRepeat(String username) {
        return userMapper.selectByUserName(username) != null;
    }

    @Override
    public int getIdByUserName(String username) {
        return userMapper.selectIdByUserName(username);
    }

    @Override
    public PageInfo<User> getAllUserByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> list = userMapper.selectAllUser();
        if (list != null) return new PageInfo<>(list);
        return null;
    }
}
