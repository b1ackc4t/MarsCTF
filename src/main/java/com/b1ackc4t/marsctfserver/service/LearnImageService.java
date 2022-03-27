package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.LearnImage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface LearnImageService extends IService<LearnImage> {
    void removeLearnImageByLid(Integer lid);
}
