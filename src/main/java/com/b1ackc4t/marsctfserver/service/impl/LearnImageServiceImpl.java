package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.LearnImageMapper;
import com.b1ackc4t.marsctfserver.pojo.LearnImage;
import com.b1ackc4t.marsctfserver.service.LearnImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class LearnImageServiceImpl extends ServiceImpl<LearnImageMapper, LearnImage> implements LearnImageService {

    @Value("${marsctf.imagePath}")
    private String imagePath;

    @Value("${marsctf.imageUrl}")
    private String imageUrl;

    final LearnImageMapper learnImageMapper;

    public LearnImageServiceImpl(LearnImageMapper learnImageMapper) {
        this.learnImageMapper = learnImageMapper;
    }

    @Override
    public void removeLearnImageByLid(Integer lid) {
        List<String> list = learnImageMapper.selectFpathByLid(lid);
        if (list != null) {
            for (String fpath : list) {
                File file = new File(imagePath + imageUrl, fpath);
                if (file.exists()) {
                    file.delete();
                }
            }
            learnImageMapper.deleteImageByLid(lid);
        }
    }
}
