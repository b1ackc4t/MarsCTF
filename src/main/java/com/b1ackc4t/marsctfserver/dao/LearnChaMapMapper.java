package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.LearnChaMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LearnChaMapMapper extends BaseMapper<LearnChaMap> {
    @Delete("delete from mc_learn_cha_map where lid=#{lid}")
    int deleteLearnChaMapByLid(Integer lid);

    @Select("select cid from mc_learn_cha_map where lid=#{lid}")
    List<Integer> selectCidByLid(Integer lid);

    @Delete("delete from mc_learn_cha_map where cid=#{cid}")
    int deleteLearnChaMapByCid(Integer cid);
}
