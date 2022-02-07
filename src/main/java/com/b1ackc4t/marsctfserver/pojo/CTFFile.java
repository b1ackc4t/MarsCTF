package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("mc_file")
public class CTFFile {
    @TableId(type = IdType.AUTO)
    private Integer fid;
    private String fpath;
    private String fname;
    private Long size;
    private String ext;

    public CTFFile() {}
    public CTFFile(String fpath) {
        this.fpath = fpath;
    }
}

