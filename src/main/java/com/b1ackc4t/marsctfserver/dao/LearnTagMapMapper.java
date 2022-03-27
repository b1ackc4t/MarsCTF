package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.LearnTagMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearnTagMapMapper extends BaseMapper<LearnTagMap> {
    @Delete("delete from mc_learn_tag_map where lid=#{lid}")
    int deleteLearnTagMapByLid(Integer lid);

    @Select("select tgid from mc_learn_tag_map where lid=#{lid}")
    List<Integer> selectTgidByLid(Integer lid);

    @Select("select a.tgid, b.tgname from mc_learn_tag_map as a left join mc_cha_tag as b on a.tgid=b.tgid where a.lid=#{lid}")
    List<ChaTag> selectTgByLid(Integer lid);

    @Delete("delete from mc_learn_tag_map where tgid=#{tgid}")
    int deleteLearnTagMapByTgid(Integer tgid);
}
