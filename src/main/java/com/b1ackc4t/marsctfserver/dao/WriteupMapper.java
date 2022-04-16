package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WriteupMapper extends BaseMapper<Writeup> {
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.descr, a.wp_tags, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.status=1 order by a.cre_time desc")
    List<Writeup> selectAllForUser();
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.text, a.wp_tags, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.status=1 and a.wid=#{wid}")
    Writeup selectByWidForUser(Integer wid);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.done=0 order by a.cre_time desc")
    List<Writeup> selectUnCheckForAdmin();
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.text, a.wp_tags, a.score, a.cre_time, a.status, a.done from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.wid=#{wid}")
    Writeup selectByWidForAdmin(Integer wid);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.cre_time, a.done, a.status, a.score from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid order by a.cre_time desc")
    List<Writeup> selectAllForAdmin();
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.status=1 and a.cid=#{cid} order by a.score desc limit #{num}")
    List<Writeup> selectWriteupByCidForRank(Integer cid, Integer num);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.descr, a.wp_tags, a.score, a.cre_time, a.done, a.status from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.uid=#{uid} order by a.cre_time desc")
    List<Writeup> selectAllForMe(Integer uid);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.descr, a.wp_tags, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.status=1 and a.uid=#{uid} order by a.cre_time desc")
    List<Writeup> selectAllForUid(Integer uid);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.text, a.wp_tags, a.score, a.cre_time, a.comment, a.done, a.status from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.wid=#{wid}")
    Writeup selectByWidForMe(Integer wid);
    @Select("select uid from mc_writeup where wid=#{wid}")
    Integer selectUidByWid(Integer wid);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.descr, a.wp_tags, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.status=1 and d.tname=#{tname} order by a.cre_time desc")
    List<Writeup> selectAllForUserByType(String tname);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.descr, a.wp_tags, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.status=1 and a.title like #{value} order by a.cre_time desc")
    List<Writeup> selectAllForUserSearch(String value);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.cre_time, a.done, a.status, a.score from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where ${key} like #{value} order by a.cre_time desc")
    List<Writeup> selectSearchForAdmin(String key, String value);
    @Select("select a.wid, a.cid, b.cname, d.tname, a.uid, c.uname, a.title, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid left join mc_cha_type as d on b.tid=d.tid where a.done=0 and ${key} like #{value} order by a.cre_time desc")
    List<Writeup> selectSearchUnCheckForAdmin(String key, String value);
}
