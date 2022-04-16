package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaComment;
import com.b1ackc4t.marsctfserver.pojo.LearnComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearnCommentMapper extends BaseMapper<LearnComment> {
    @Select("select a.*, b.uname from mc_learn_comment as a left join mc_user as b on a.uid=b.uid where lid=#{lid} order by a.create_time desc")
    List<LearnComment> selectCommentByLid(Integer lid);

    @Select("select uid from mc_learn_comment where lcid=#{lcid}")
    Integer selectUidByLcid(Integer lcid);

    @Delete("delete from mc_learn_comment where lid=#{lid}")
    int deleteByLid(Integer lid);

    @Delete("delete from mc_learn_comment where uid=#{uid}")
    int deleteByUid(Integer uid);
}
