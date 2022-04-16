package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Challenge;
import com.b1ackc4t.marsctfserver.pojo.DockerContainer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChallengeMapper extends BaseMapper<Challenge> {

    @Select("select a.cid, a.cname, descr, a.score, b.tname, a.fid, a.finished_num, a.master_uid, c.uname as master_name, a.is_dynamic from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid left join mc_user as c on a.master_uid=c.uid where a.exposed=1 and a.cid=#{id}")
    Challenge selectByIdForUser(Integer id);

    @Select("select a.*, b.tname, c.uname as master_name from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid left join mc_user as c on a.master_uid=c.uid where cid=#{id}")
    Challenge selectByIdForAdmin(Integer id);

    @Select("select a.cid, a.cname, a.descr, a.score, b.tname, a.exposed, a.cretime, a.is_dynamic, a.download_ok from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid")
    List<Challenge> selectAllForAdmin();

    @Select("select a.cid, a.cname, a.score, c.tname, a.finished_num, b.status from mc_challenge as a left join mc_user_cha_map as b on a.cid=b.cid and b.uid=#{uid} left join mc_cha_type as c on a.tid=c.tid where exposed=1 and (download_ok=1 or is_dynamic=0)")
    List<Challenge> selectAllForUser(Integer uid);

    @Select("select a.cid, a.cname, a.score, c.tname, a.finished_num, b.status from mc_challenge as a left join mc_user_cha_map as b on a.cid=b.cid and b.uid=#{uid} left join mc_cha_type as c on a.tid=c.tid where exposed=1 and c.tname=#{type}")
    List<Challenge> selectForUserByType(Integer uid, String type);

    @Select("select a.cid, a.cname, a.score, e.tname, a.finished_num, b.status from mc_challenge as a left join mc_user_cha_map as b on a.cid=b.cid and b.uid=#{uid} right join mc_cha_tag_map as c on a.cid=c.cid left join mc_cha_tag as d on c.tgid=d.tgid left join mc_cha_type as e on a.tid=e.tid where exposed=1 and d.tgname=#{tag}")
    List<Challenge> selectForUserByTag(Integer uid, String tag);

    @Select("select fid from mc_challenge where cid=#{cid}")
    Challenge selectFidByCid(Integer cid);

    @Select("select a.cid, a.cname, a.score, a.finished_num, b.tname from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid where exposed=1 and cid=#{id}")
    Challenge selectByIdForSubmitFlag(Integer id);

    @Select("select a.cid, a.cname, a.score, b.tname, a.finished_num from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid where exposed=1")
    List<Challenge> selectAllForAll();

    @Select("select a.cid, a.cname, a.score, b.tname, a.finished_num from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid where exposed=1 and b.tname=#{type}")
    List<Challenge> selectForAllByType(String type);

    @Select("select a.cid, a.cname, a.score, b.tname, a.finished_num from mc_challenge as a right join mc_cha_tag_map as c on a.cid=c.cid left join mc_cha_tag as d on c.tgid=d.tgid left join mc_cha_type as b on a.tid=b.tid where exposed=1 and d.tgname=#{tag}")
    List<Challenge> selectForAllByTag(String tag);

    @Select("select wid from mc_writeup where cid=#{cid}")
    List<Integer> selectWidByCid(Integer cid);

    @Select("select cid, cname from mc_challenge")
    List<Challenge> selectSimpleAll();

    @Select("select fid, is_dynamic, image_name, download_ok from mc_challenge where cid=#{cid}")
    Challenge selectInfoByCid(Integer cid);

    @Select("select image_name, src_port, cpu_limit, mem_limit, is_dynamic_flag, flag_prefix, flag from mc_challenge where exposed=1 and download_ok=1 and cid=#{cid}")
    Challenge selectContainerInfoByCid(Integer cid);

    @Select("select is_dynamic_flag, flag from mc_challenge where cid=#{cid}")
    Challenge selectStaticFlag(Integer cid);

    @Select("select flag from mc_docker_container where uid=#{uid} and cid=#{cid}")
    DockerContainer selectDynamicFlag(Integer uid, Integer cid);

    @Select("select a.cid, a.cname, a.descr, a.score, b.tname, a.exposed, a.cretime, a.is_dynamic, a.download_ok from mc_challenge as a left join mc_cha_type as b on a.tid=b.tid where ${key} like #{value}")
    List<Challenge> selectSearchAllForAdmin(String key, String value);

}
