package com.b1ackc4t.marsctfserver.service.impl;

import com.b1ackc4t.marsctfserver.dao.CTFFileMapper;
import com.b1ackc4t.marsctfserver.pojo.CTFFile;
import com.b1ackc4t.marsctfserver.pojo.ReturnRes;
import com.b1ackc4t.marsctfserver.service.CTFFileService;
import com.b1ackc4t.marsctfserver.util.generator.SnowFlakeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@Service
public class CTFFileServiceImpl extends ServiceImpl<CTFFileMapper, CTFFile> implements CTFFileService {

    @Value("${marsctf.uploadPath}")
    private String uploadPath;

    final CTFFileMapper ctfFileMapper;

    @Autowired
    public CTFFileServiceImpl(CTFFileMapper ctfFileMapper) {
        this.ctfFileMapper = ctfFileMapper;
    }


    @Override
    public ReturnRes upload(MultipartFile file) {
        if (file.isEmpty()) {
            return new ReturnRes(false, "上传失败，请选择文件");
        }

        String fileName = file.getOriginalFilename();
        String snowId = String.valueOf(SnowFlakeUtil.generatorUid());
        File pathFile = new File(uploadPath, snowId); // 为每个文件单独创建一个文件夹 文件夹名采用雪花算法
        String path = pathFile.toString();
        if (!pathFile.mkdir()) {
            return new ReturnRes(false, "上传失败，服务器错误");
        }
        File dest = new File(path, fileName);
        try {
            file.transferTo(dest);  // 上传成功
            CTFFile ctfFile = new CTFFile(new File(snowId, fileName).toString());
            int lastIndexOf = fileName.lastIndexOf(".");
            String ext = lastIndexOf != -1 ? fileName.substring(lastIndexOf + 1) : "";
            ctfFile.setFname(dest.getName());
            ctfFile.setSize(dest.length()); // 单位字节
            ctfFile.setExt(ext);
            this.save(ctfFile);
            return new ReturnRes(true, ctfFile.getFid());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ReturnRes(false, "上传失败，服务器错误");
    }

    @Override
    public ReturnRes removeCTFFile(Integer fid) {
        CTFFile ctfFile = this.getById(fid);
        if (ctfFile == null) {
            return new ReturnRes(false, "文件不存在 可能已经被删除");
        }
        File file = new File(uploadPath, ctfFile.getFpath());
        if (file.exists()) {
            file.delete();
        }
        File parentDir = file.getParentFile();
        if (parentDir.exists()) {
            parentDir.delete();
        }

        if (this.removeById(fid)) {
            return new ReturnRes(true, fid);
        } else {
            return new ReturnRes(false, "文件不存在 可能已经被删除");
        }
    }

    @Override
    public ReturnRes download(Integer fid, HttpServletResponse httpServletResponse) {
        CTFFile ctfFile = super.getById(fid);
        FileInputStream fileInputStream = null;
        OutputStream outputStream = null;
        if (ctfFile != null) {
            try {
                fileInputStream = new FileInputStream(new File(uploadPath, ctfFile.getFpath()));
                httpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + ctfFile.getFname());
                BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
                int i = 0;
                byte[] buf = new byte[1024 * 8];
                outputStream = httpServletResponse.getOutputStream();
                while ((i = bufferedInputStream.read(buf, 0, buf.length)) != -1) {
                    outputStream.write(buf, 0, i);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return new ReturnRes(false, "文件不存在");
            } catch (IOException e) {
                e.printStackTrace();
                return new ReturnRes(false, "文件读取异常");
            } finally {
                try {
                    if (fileInputStream != null) fileInputStream.close();
                    if (outputStream != null) {
                        outputStream.flush();
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return new ReturnRes(true);
        } else {
            return new ReturnRes(false, "文件不存在");
        }
    }
}
