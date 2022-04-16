package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.WpComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WpCommentMapper extends BaseMapper<WpComment> {
    @Select("select a.*, b.uname from mc_wp_comment as a left join mc_user as b on a.uid=b.uid where wid=#{wid} order by a.create_time desc")
    List<WpComment> selectCommentByWid(Integer wid);

    @Select("select uid from mc_wp_comment where wcid=#{wcid}")
    Integer selectUidByWcid(Integer wcid);

    @Delete("delete from mc_wp_comment where wid=#{wid}")
    int deleteByWid(Integer wid);

    @Delete("delete from mc_wp_comment where uid=#{uid}")
    int deleteByUid(Integer uid);
}
