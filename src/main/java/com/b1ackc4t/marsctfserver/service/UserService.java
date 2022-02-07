package com.b1ackc4t.marsctfserver.service;


import com.b1ackc4t.marsctfserver.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

public interface UserService extends IService<User> {

    boolean usernameIsRepeat(String username);

    User getByUserName(String username);

    int getIdByUserName(String username);

    PageInfo<User> getAllUserByPage(int pageNum, int pageSize);
}
