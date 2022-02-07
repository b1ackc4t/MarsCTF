package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChaTagMapper extends BaseMapper<ChaTag> {
    @Select("select * from mc_cha_tag where tname=#{type}")
    List<ChaTag> selectByType(String type);
}
