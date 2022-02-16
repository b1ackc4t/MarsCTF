package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChaTypeMapper extends BaseMapper<ChaType> {
    @Select("select * from mc_cha_type order by tid")
    List<ChaType> selectAll();

    @Select("select cid from mc_challenge where tid=#{tid}")
    List<Integer> selectCidByTid(Integer tid);

    @Select("select tgid from mc_cha_tag where tid=#{tid}")
    List<Integer> selectTgidByTid(Integer tid);
}
