package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChaCommentMapper extends BaseMapper<ChaComment> {
    @Select("select a.*, b.uname from mc_cha_comment as a left join mc_user as b on a.uid=b.uid where cid=#{cid} order by a.create_time desc")
    List<ChaComment> selectCommentByCid(Integer cid);

    @Select("select uid from mc_cha_comment where ccid=#{ccid}")
    Integer selectUidByCcid(Integer ccid);

    @Delete("delete from mc_cha_comment where cid=#{cid}")
    int deleteByCid(Integer cid);

    @Delete("delete from mc_cha_comment where uid=#{uid}")
    int deleteByUid(Integer uid);
}
