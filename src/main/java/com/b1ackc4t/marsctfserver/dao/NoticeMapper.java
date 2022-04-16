package com.b1ackc4t.marsctfserver.dao;

import com.b1ackc4t.marsctfserver.pojo.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {
    @Select("select a.nid, a.title, a.create_time, a.uid, b.uname from mc_notice as a left join mc_user as b on a.uid=b.uid order by a.create_time desc")
    List<Notice> selectNoticeByPageForAdmin();

    @Select("select a.*, b.uname from mc_notice as a left join mc_user as b on a.uid=b.uid where a.nid=#{nid}")
    Notice selectNoticeByIdForAdmin(Integer nid);

    @Select("select a.nid, a.title, a.create_time, a.uid, b.uname, a.descr from mc_notice as a left join mc_user as b on a.uid=b.uid order by a.create_time desc")
    List<Notice> selectNoticeByPage();

    @Select("select a.nid, a.title, a.create_time, a.uid, b.uname from mc_notice as a left join mc_user as b on a.uid=b.uid where ${key} like #{value} order by a.create_time desc")
    List<Notice> selectSearchNoticeByPageForAdmin(String key, String value);

}
