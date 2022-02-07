package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaRankPojo;
import com.b1ackc4t.marsctfserver.pojo.UserChaMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserChaMapMapper extends BaseMapper<UserChaMap> {
    @Select("select status from mc_user_cha_map where uid=#{uid} and cid=#{cid}")
    String selectStatus(Integer uid, Integer cid);

    @Update("update mc_user_cha_map set status=#{status},finish_time=#{finishTime},`rank`=#{rank} where uid=#{uid} and cid=#{cid}")
    int updateById1(UserChaMap userChaMap);

    @Update("update mc_user_cha_map set status=#{status},finish_time=#{finishTime} where uid=#{uid} and cid=#{cid}")
    int updateById2(UserChaMap userChaMap);

    @Delete("delete from mc_user_cha_map where cid=#{cid}")
    int deleteByCid(Integer cid);

    @Delete("delete from mc_user_cha_map where uid=#{uid}")
    int deleteByUid(Integer uid);
}
