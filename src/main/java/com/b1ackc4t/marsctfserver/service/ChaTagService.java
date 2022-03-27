package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ChaTag;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ChaTagService extends IService<ChaTag> {
    PageInfo<ChaTag> getAllTagByPageForAdmin(int pageNum, int pageSize);
    ReturnRes getTagsByType(String type);
    ReturnRes getTags();
    ReturnRes removeTag(ChaTag chaTag);
}
