package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface StatMapper {
    @Select("select count(*) from mc_challenge")
    int selectChaCount();

    @Select("select count(*) from mc_learning")
    int selectLearningCount();

    @Select("select count(*) from mc_user")
    int selectUserCount();

    @Select("select tname, count(*) as num from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid group by b.tname")
    List<Map> selectChaCountByType();

    @Select("select a.uid, a.uname, a.score from mc_user as a order by a.score desc limit #{num}")
    List<User> selectRank(int num);

    @Select("select a.uid, a.uname, a.score, a.unit, a.web, a.pwn, a.re, a.crypto, a.misc, a.other from mc_user as a order by a.score desc")
    List<User> selectRankView();
}
