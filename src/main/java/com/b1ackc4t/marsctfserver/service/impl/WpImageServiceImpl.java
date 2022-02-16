package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.WpImageMapper;
import com.b1ackc4t.marsctfserver.pojo.WpImage;
import com.b1ackc4t.marsctfserver.service.WpImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class WpImageServiceImpl extends ServiceImpl<WpImageMapper, WpImage> implements WpImageService {
    final WpImageMapper wpImageMapper;

    @Value("${marsctf.imagePath}")
    private String imagePath;

    @Value("${marsctf.imageUrl}")
    private String imageUrl;

    @Autowired
    public WpImageServiceImpl(WpImageMapper wpImageMapper) {
        this.wpImageMapper = wpImageMapper;
    }

    @Override
    public void removeWpImageByWid(Integer wid) {
        List<String> list = wpImageMapper.selectFpathByWid(wid);
        if (list != null) {
            for (String fpath : list) {
                File file = new File(imagePath + imageUrl, fpath);
                if (file.exists()) {
                    file.delete();
                }
            }
            wpImageMapper.deleteImageByWid(wid);
        }
    }
}
