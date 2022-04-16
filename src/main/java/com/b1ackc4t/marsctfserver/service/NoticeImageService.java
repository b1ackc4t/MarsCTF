package com.b1ackc4t.marsctfserver.service;


import com.b1ackc4t.marsctfserver.pojo.NoticeImage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoticeImageService extends IService<NoticeImage> {
    void removeImageByNid(Integer nid);
}
