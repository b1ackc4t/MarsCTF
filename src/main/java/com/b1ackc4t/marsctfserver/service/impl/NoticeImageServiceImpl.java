package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.NoticeImageMapper;
import com.b1ackc4t.marsctfserver.pojo.NoticeImage;
import com.b1ackc4t.marsctfserver.service.NoticeImageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class NoticeImageServiceImpl extends ServiceImpl<NoticeImageMapper, NoticeImage> implements NoticeImageService {
    final NoticeImageMapper noticeImageMapper;

    @Value("${marsctf.imagePath}")
    private String imagePath;

    @Value("${marsctf.imageUrl}")
    private String imageUrl;

    @Autowired
    public NoticeImageServiceImpl(NoticeImageMapper noticeImageMapper) {
        this.noticeImageMapper = noticeImageMapper;
    }


    @Override
    public void removeImageByNid(Integer nid) {
        List<String> list = noticeImageMapper.selectFpathByNid(nid);
        if (list != null) {
            for (String fpath : list) {
                File file = new File(imagePath + imageUrl, fpath);
                if (file.exists()) {
                    file.delete();
                }
            }
            noticeImageMapper.deleteImageByNid(nid);
        }
    }
}
