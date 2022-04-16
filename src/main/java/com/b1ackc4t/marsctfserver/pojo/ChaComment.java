package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class ChaComment {
    private Integer cid;
    private Integer uid;
    @TableId(type = IdType.AUTO)
    private Integer ccid;
    private String text;
    private Date createTime;

    private String uname;
}
