package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaRankPojo;
import com.b1ackc4t.marsctfserver.pojo.User;
import com.b1ackc4t.marsctfserver.pojo.UserChartPojo;
import com.b1ackc4t.marsctfserver.service.ChallengeService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserScoreMapper {
    @Select("SELECT a.uid, b.uname, a.finish_time, a.`rank` FROM `mc_user_cha_map` as a left join `mc_user` as b on a.uid=b.uid where cid=#{cid} and status='" + ChallengeService.SUCCESS_STATUS + "' order by `rank` limit #{num}")
    List<ChaRankPojo> selectChaRankInfo(Integer cid, Integer num);

    @Select("select uid, uname, web, re, crypto, pwn, misc, other, score from mc_user")
    List<User> selectAllScore();

    @Select("select b.tname, sum(a.score) as total_score from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid group by b.tname;")
    List<UserChartPojo> selectAllChart();

    @Select("select c.tname, sum(b.score) as total_score from mc_user_cha_map as a left join mc_challenge as b  on a.cid=b.cid left join mc_cha_type as c on b.tid=c.tid where a.uid=#{uid} group by c.tname;")
    List<UserChartPojo> selectUserChart(Integer uid);

}
