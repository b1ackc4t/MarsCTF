package com.b1ackc4t.marsctfserver.dao;


import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.Learning;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearningMapper extends BaseMapper<Learning> {

    @Select("select a.lid, a.title, a.uid, b.uname, a.tid, c.tname, a.cre_time from mc_learning as a left join mc_user as b on a.uid=b.uid left join mc_cha_type as c on a.tid=c.tid")
    List<Learning> selectAllLearningForAdmin();

    @Select("select a.lid, a.title, a.tid, c.tname, a.text, a.has_train from mc_learning as a left join mc_cha_type as c on a.tid=c.tid where a.lid=#{lid}")
    Learning selectLearningByLidForAdminEdit(Integer lid);

    @Select("select a.lid, a.title, a.uid, b.uname, a.tid, c.tname, a.cre_time, a.descr from mc_learning as a left join mc_user as b on a.uid=b.uid left join mc_cha_type as c on a.tid=c.tid order by a.cre_time desc")
    List<Learning> selectAllLearningForUser();

    @Select("select a.lid, a.title, a.uid, b.uname, a.tid, c.tname, a.cre_time, a.descr from mc_learning as a left join mc_user as b on a.uid=b.uid left join mc_cha_type as c on a.tid=c.tid where c.tname=#{tname} order by a.cre_time desc")
    List<Learning> selectLearningByTypeForUser(String tname);

    @Select("select a.lid, a.title, a.uid, b.uname, a.tid, c.tname, a.cre_time, a.descr from mc_learning as a left join mc_user as b on a.uid=b.uid left join mc_cha_type as c on a.tid=c.tid right join mc_learn_tag_map as d on a.lid=d.lid left join mc_cha_tag as e on d.tgid=e.tgid where e.tgname=#{tgname} order by a.cre_time desc")
    List<Learning> selectLearningByTagForUser(String tgname);

    @Select("select a.lid, a.title, a.uid, b.uname, a.tid, c.tname, a.cre_time, a.text, a.has_train from mc_learning as a left join mc_user as b on a.uid=b.uid left join mc_cha_type as c on a.tid=c.tid where a.lid=#{lid}")
    Learning selectLearningByLidForUser(Integer lid);

    @Select("select a.cid, a.cname, a.score, c.tname, a.finished_num, b.status from mc_challenge as a right join mc_learn_cha_map as d on a.cid=d.cid left join mc_user_cha_map as b on a.cid=b.cid and b.uid=#{uid} left join mc_cha_type as c on a.tid=c.tid where d.lid=#{lid}")
    List<Challenge> selectChallengeByLidForUser(Integer uid, Integer lid);

    @Select("select lid from mc_learning where tid=#{tid}")
    List<Integer>  selectLidByTid(Integer tid);





}
