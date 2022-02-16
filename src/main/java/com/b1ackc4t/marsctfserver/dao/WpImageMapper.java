package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.UserChaMap;
import com.b1ackc4t.marsctfserver.pojo.WpImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface WpImageMapper extends BaseMapper<WpImage> {
    // 用于保存wp时把临时图片加上wp的id，以便今后删除
    @Update("update mc_wp_image set wid=#{wid} where wid=99999999")
    int updateImageInfo(Integer wid);

    @Delete("delete from mc_wp_image where wid=#{wid}")
    int deleteImageByWid(Integer wid);

    @Select("select fpath from mc_wp_image where wid=#{wid}")
    List<String> selectFpathByWid(Integer wid);
}
