package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.WpImage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface WpImageService extends IService<WpImage> {
    void removeWpImageByWid(Integer wid);
}
