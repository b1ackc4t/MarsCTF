package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.ChaType;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;

public interface ChaTypeService extends IService<ChaType> {

    ReturnRes getTypesByPageForAdmin(int pageSize, int pageNum);
    ReturnRes updateType(ChaType chaType);
    ReturnRes saveType(ChaType chaType);
    ReturnRes removeType(ChaType chaType);

}
