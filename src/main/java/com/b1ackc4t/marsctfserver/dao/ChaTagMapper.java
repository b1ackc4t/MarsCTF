package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChaTagMapper extends BaseMapper<ChaTag> {
    @Select("select a.*, b.tname from mc_cha_tag as a left join mc_cha_type as b on a.tid=b.tid where b.tname=#{type}")
    List<ChaTag> selectByType(String type);

    @Select("select a.*, b.tname from mc_cha_tag as a left join mc_cha_type as b on a.tid=b.tid")
    List<ChaTag> selectAllTag();

    @Select("select a.*, b.tname from mc_cha_tag as a left join mc_cha_type as b on a.tid=b.tid where a.tgid=#{tgid}")
    ChaTag selectTagById(Integer tgid);

    @Select("select a.*, b.tname, count(c.cid) as chaNum, (select count(tgid) from mc_learn_tag_map where tgid=a.tgid) as learnNum from mc_cha_tag as a left join mc_cha_type as b on a.tid=b.tid left join mc_cha_tag_map as c on a.tgid=c.tgid group by a.tgid")
    List<ChaTag> selectAllTagForAdmin();

    @Select("select a.*, b.tname, count(c.cid) as chaNum, (select count(tgid) from mc_learn_tag_map where tgid=a.tgid) as learnNum from mc_cha_tag as a left join mc_cha_type as b on a.tid=b.tid left join mc_cha_tag_map as c on a.tgid=c.tgid where ${key} like #{value} group by a.tgid")
    List<ChaTag> selectSearchTagForAdmin(String key, String value);
}
