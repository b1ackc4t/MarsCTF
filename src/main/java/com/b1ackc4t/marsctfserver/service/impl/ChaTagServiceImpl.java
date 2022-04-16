package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.ChaTagMapMapper;
import com.b1ackc4t.marsctfserver.dao.ChaTagMapper;
import com.b1ackc4t.marsctfserver.dao.LearnTagMapMapper;
import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.ChaTagService;
import com.b1ackc4t.marsctfserver.util.CommonUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaTagServiceImpl extends ServiceImpl<ChaTagMapper, ChaTag> implements ChaTagService {

    final ChaTagMapper chaTagMapper;
    final ChaTagMapMapper chaTagMapMapper;
    final LearnTagMapMapper learnTagMapMapper;

    public ChaTagServiceImpl(ChaTagMapper chaTagMapper, ChaTagMapMapper chaTagMapMapper, LearnTagMapMapper learnTagMapMapper) {
        this.chaTagMapper = chaTagMapper;
        this.chaTagMapMapper = chaTagMapMapper;
        this.learnTagMapMapper = learnTagMapMapper;
    }

    @Override
    public PageInfo<ChaTag> getAllTagByPageForAdmin(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ChaTag> list = chaTagMapper.selectAllTagForAdmin();
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
        chaTagMapMapper.deleteByTgid(chaTag.getTgid()); // 删除掉题目中携带的标签
        learnTagMapMapper.deleteLearnTagMapByTgid(chaTag.getTgid()); // 删除掉learning中携带的标签
        if (removeById(chaTag.getTgid())) {
            return new ReturnRes(true, chaTag.getTgname() + "删除成功");
        }
        return new ReturnRes(false, "删除失败");
    }

    @Override
    public ReturnRes searchTagPage(String key, String value, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        String[] whiteList = new String[]{"tgid", "tgname", "tname"};
        if (CommonUtil.strArrayIsHave(whiteList, key)) {
            List<ChaTag> list = chaTagMapper.selectSearchTagForAdmin(key, "%" + value + "%");
            if (list != null) return new ReturnRes(true, new PageInfo<>(list), "搜索成功");
            return new ReturnRes(false, "搜索失败");
        } else {
            return new ReturnRes(false, "师傅请勿尝试不安全的参数");
        }
    }
}
