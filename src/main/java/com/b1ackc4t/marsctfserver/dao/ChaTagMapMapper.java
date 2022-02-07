package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaTagMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChaTagMapMapper extends BaseMapper<ChaTagMap> {
    @Select("select tgname from mc_cha_tag_map where cid=#{cid}")
    List<ChaTagMap> selectTgnameByCid(Integer cid);

    @Delete("delete from mc_cha_tag_map where cid=#{cid}")
    boolean deleteByCid(Integer cid);


}
