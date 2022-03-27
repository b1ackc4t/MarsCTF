package com.b1ackc4t.marsctfserver.service;

import com.b1ackc4t.marsctfserver.pojo.CTFFile;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;

public interface CTFFileService extends IService<CTFFile> {
    ReturnRes upload(MultipartFile file);
    ReturnRes removeCTFFile(Integer fid);
    ReturnRes download(Integer fid, HttpServletResponse httpServletResponse);
    ReturnRes uploadImageForWP(MultipartFile file, Integer wid);
    ReturnRes uploadImageForLearn(MultipartFile file, Integer lid);
}
