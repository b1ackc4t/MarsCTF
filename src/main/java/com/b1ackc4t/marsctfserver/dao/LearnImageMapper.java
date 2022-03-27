package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.LearnImage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface LearnImageMapper extends BaseMapper<LearnImage> {
    // 用于保存learning时把临时图片加上learning的id，以便今后删除
    @Update("update mc_learn_image set lid=#{lid} where lid=99999999")
    int updateImageInfo(Integer lid);

    @Delete("delete from mc_learn_image where lid=#{lid}")
    int deleteImageByLid(Integer lid);

    @Select("select fpath from mc_learn_image where lid=#{lid}")
    List<String> selectFpathByLid(Integer lid);
}
