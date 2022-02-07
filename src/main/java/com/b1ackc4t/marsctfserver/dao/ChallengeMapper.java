package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChallengeMapper extends BaseMapper<Challenge> {

    @Select("select cid, cname, descr, score, tname, fid, finished_num, master_uid, master_name from mc_challenge where exposed=1 and cid=#{id}")
    Challenge selectByIdForUser(Integer id);

    @Select("select * from mc_challenge where cid=#{id}")
    Challenge selectByIdForAdmin(Integer id);

    @Select("select cid, cname, descr, score, tname, exposed, cretime from mc_challenge")
    List<Challenge> selectAllForAdmin();

    @Select("select a.cid, a.cname, a.score, a.tname, a.finished_num, b.status from mc_challenge as a left join mc_user_cha_map as b on a.cid=b.cid and b.uid=#{uid} where exposed=1")
    List<Challenge> selectAllForUser(Integer uid);

    @Select("select a.cid, a.cname, a.score, a.tname, a.finished_num, b.status from mc_challenge as a left join mc_user_cha_map as b on a.cid=b.cid and b.uid=#{uid} where exposed=1 and a.tname=#{type}")
    List<Challenge> selectForUserByType(Integer uid, String type);

    @Select("select a.cid, a.cname, a.score, a.tname, a.finished_num, b.status from mc_challenge as a left join mc_user_cha_map as b on a.cid=b.cid and b.uid=#{uid} right join mc_cha_tag_map as c on a.cid=c.cid and c.tgname=#{tag} where exposed=1")
    List<Challenge> selectForUserByTag(Integer uid, String tag);

    @Select("select fid from mc_challenge where cid=#{cid}")
    Challenge selectFidByCid(Integer cid);

    @Select("select cid, cname, score, flag, finished_num, tname from mc_challenge where exposed=1 and cid=#{id}")
    Challenge selectByIdForSubmitFlag(Integer id);

    @Select("select cid, cname, score, tname, finished_num from mc_challenge where exposed=1")
    List<Challenge> selectAllForAll();

    @Select("select a.cid, a.cname, a.score, a.tname, a.finished_num from mc_challenge as a where exposed=1 and a.tname=#{type}")
    List<Challenge> selectForAllByType(String type);

    @Select("select a.cid, a.cname, a.score, a.tname, a.finished_num from mc_challenge as a right join mc_cha_tag_map as c on a.cid=c.cid and c.tgname=#{tag} where exposed=1")
    List<Challenge> selectForAllByTag(String tag);
}
