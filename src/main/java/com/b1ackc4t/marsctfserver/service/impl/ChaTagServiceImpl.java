package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChaTagMapMapper;
import com.b1ackc4t.marsctfserver.dao.ChaTagMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaTagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaTagServiceImpl extends ServiceImpl<ChaTagMapper, ChaTag> implements ChaTagService {

    final ChaTagMapper chaTagMapper;
    final ChaTagMapMapper chaTagMapMapper;

    public ChaTagServiceImpl(ChaTagMapper chaTagMapper, ChaTagMapMapper chaTagMapMapper) {
        this.chaTagMapper = chaTagMapper;
        this.chaTagMapMapper = chaTagMapMapper;
    }

    @Override
    public PageInfo<ChaTag> getAllTagByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ChaTag> list = chaTagMapper.selectAllTag();
        if (list != null) return new PageInfo<>(list);
        return null;
    }

    @Override
    public ReturnRes getTagsByType(String type) {
        List<ChaTag> chaTags = chaTagMapper.selectByType(type);
        if (chaTags != null) {
            return new ReturnRes(true, chaTags, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes getTags() {
        List<ChaTag> chaTags = chaTagMapper.selectAllTag();
        if (chaTags != null) {
            return new ReturnRes(true, chaTags, "查询成功");
        }
        return new ReturnRes(false, "查询失败");
    }

    @Override
    public ReturnRes removeTag(ChaTag chaTag) {
        chaTagMapMapper.deleteByTgid(chaTag.getTgid());
        if (removeById(chaTag.getTgid())) {
            return new ReturnRes(true, chaTag.getTgname() + "删除成功");
        }
        return new ReturnRes(false, "删除失败");
    }
}
