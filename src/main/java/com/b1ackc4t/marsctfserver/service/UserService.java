package com.b1ackc4t.marsctfserver.service;


import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

public interface UserService extends IService<User> {

    boolean usernameIsRepeat(String username);

    User getByUserName(String username);

    int getIdByUserName(String username);

    PageInfo<User> getAllUserByPage(int pageNum, int pageSize);

    ReturnRes searchUserByPage(String key, String value, int pageNum, int pageSize);
}
