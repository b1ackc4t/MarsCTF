package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    @Select("select * from mc_user where uname=#{uname} and upassword=#{upassword}")
    User selectByUserNameAndPassword(String uname, String upassword);

    @Select("select uid, upassword, role from mc_user where uname=#{uname}")
    User selectLoginByUserName(String uname);


    @Select("select * from mc_user where uname=#{uname}")
    User selectByUserName(String uname);

    @Select("select uid from mc_user where uname=#{uname}")
    int selectIdByUserName(String uname);

    @Select("select * from mc_user where uid=#{uid}")
    User selectUserByIdForAdmin(Integer uid);

    @Select("select uid, uname, sex, skill, email, unit, sign, role, score from mc_user where uid=#{uid}")
    User selectUserByIdForUser(Integer uid);

    @Select("select * from mc_user")
    List<User> selectAllUser();


}
