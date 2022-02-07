package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WriteupMapper extends BaseMapper<Writeup> {
    @Select("select a.wid, a.cid, b.cname, b.tname, a.uid, c.uname, a.title, a.descr, a.wp_tags, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid where a.status=1 order by a.cre_time desc")
    List<Writeup> selectAllForUser();
    @Select("select a.wid, a.cid, b.cname, b.tname, a.uid, c.uname, a.title, a.text, a.wp_tags, a.score, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid where a.status=1 and a.wid=#{wid}")
    Writeup selectByWid(Integer wid);
    @Select("select a.wid, a.cid, b.cname, b.tname, a.uid, c.uname, a.title, a.cre_time from mc_writeup as a left join mc_challenge as b on b.cid=a.cid left join mc_user as c on c.uid=a.uid where a.done=0 order by a.cre_time desc")
    List<Writeup> selectUnCheckForAdmin();
}
