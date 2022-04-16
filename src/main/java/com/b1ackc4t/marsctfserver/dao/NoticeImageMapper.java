package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.NoticeImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface NoticeImageMapper extends BaseMapper<NoticeImage> {
    @Update("update mc_notice_image set nid=#{wid} where nid=99999999")
    int updateImageInfo(Integer nid);

    @Delete("delete from mc_notice_image where nid=#{nid}")
    int deleteImageByNid(Integer nid);

    @Select("select fpath from mc_notice_image where nid=#{nid}")
    List<String> selectFpathByNid(Integer nid);
}
