package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaTagMap;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChaTagMapMapper extends BaseMapper<ChaTagMap> {
    @Select("select a.tgid, b.tgname from mc_cha_tag_map as a left join mc_cha_tag as b on a.tgid=b.tgid where cid=#{cid}")
    List<ChaTagMap> selectTgnameByCid(Integer cid);

    @Delete("delete from mc_cha_tag_map where cid=#{cid}")
    boolean deleteByCid(Integer cid);

    @Delete("delete from mc_cha_tag_map where tgid=#{tgid}")
    boolean deleteByTgid(Integer tgid);

}
