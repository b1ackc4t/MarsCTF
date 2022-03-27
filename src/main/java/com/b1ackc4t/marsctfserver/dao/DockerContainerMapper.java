package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.DockerContainer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface DockerContainerMapper extends BaseMapper<DockerContainer> {
    @Select("select container_id from mc_docker_container where uid=#{uid}")
    List<String> selectContainerIdByUid(Integer uid);

    @Select("select count(*) from mc_docker_container where uid=#{uid}")
    int selectCountByUid(Integer uid);

    @Select("select count(*) from mc_docker_container")
    int selectCount();

    @Select("select a.*, b.image_name, b.src_port, b.cpu_limit, b.mem_limit from mc_docker_container as a left join mc_challenge as b on a.cid=b.cid")
    List<DockerContainer> selectAllContainer();

    @Select("select container_id from mc_docker_container where cid=#{cid}")
    List<String> selectContainerIdByCid(Integer cid);

    @Select("select container_id from mc_docker_container where uid=#{uid} and cid=#{cid} and status='running'")
    String selectContainerIdByCidUid(Integer cid, Integer uid);

    @Select("select start_time, dst_port, add_time, add_count from mc_docker_container where uid=#{uid} and cid=#{cid} and status='running'")
    DockerContainer selectContainerByCidUid(Integer cid, Integer uid);

    @Delete("delete from mc_docker_container where cid=#{cid}")
    int deleteByCid(Integer cid);

    @Delete("delete from mc_docker_container where uid=#{uid} and cid=#{cid}")
    int deleteByUidCid(Integer uid, Integer cid);

    @Delete("delete from mc_docker_container where uid=#{uid}")
    int deleteByUid(Integer uid);

    @Delete("delete from mc_docker_container where container_id=#{id}")
    int deleteById(String id);

    @Select("select (@i:=@i+1) as no, a.*, b.uname, c.cname from mc_docker_container as a left join mc_user as b on a.uid=b.uid left join mc_challenge as c on a.cid=c.cid inner join (select @i:=0) as it")
    List<DockerContainer> selectAllContainerForAdmin();

    @Select("select container_id, start_time, add_time from mc_docker_container")
    List<DockerContainer> selectTimeInfo();

    @Select("select add_time from mc_docker_container where uid=#{uid} and cid=#{cid}")
    Integer selectAddTimeByUidCid(Integer uid, Integer cid);

    @Update("update mc_docker_container set add_time=#{time} where uid=#{uid} and cid=#{cid}")
    int updateTimeByUidCid(Integer time, Integer uid, Integer cid);

    @Select("select add_count, add_time from mc_docker_container where uid=#{uid} and cid=#{cid}")
    DockerContainer selectAddTimeForUserByUidCid(Integer uid, Integer cid);

    @Update("update mc_docker_container set add_time=#{time}, add_count=#{count} where uid=#{uid} and cid=#{cid}")
    int updateTimeForUserByUidCid(Integer time, Integer count, Integer uid, Integer cid);
}
