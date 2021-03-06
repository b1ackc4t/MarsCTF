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

    @Select("select a.cid, a.status, b.cname, c.tname, a.rank, a.finish_time, b.score from mc_user_cha_map as a left join mc_challenge as b  on a.cid=b.cid left join mc_cha_type as c on b.tid=c.tid where a.uid=#{uid} order by a.finish_time desc")
    List<UserChaMap> selectChaStatusForUser(Integer uid);
}
