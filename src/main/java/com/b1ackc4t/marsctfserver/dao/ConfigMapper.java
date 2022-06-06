package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Config;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ConfigMapper extends BaseMapper<Config> {
    @Select("select * from mc_config")
    Config selectConfig();

    @Select("select docker_min_port, docker_max_port, ret_domain, docker_time, max_container_count, add_time_count, user_max_container from mc_config")
    Config selectDockerConfig();

    @Select("select docker_time from mc_config")
    Integer selectTimeConfig();

    @Select("select add_time_count, add_time_num from mc_config")
    Config selectAddTimeForUserConfig();

    @Select("select docker_host, docker_registry, is_tls from mc_config")
    Config selectDockerUtilConfig();
}
