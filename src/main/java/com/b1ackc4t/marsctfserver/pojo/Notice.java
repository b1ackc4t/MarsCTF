package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Notice {
    @TableId(type = IdType.AUTO)
    private Integer nid;
    private String title;
    private String text;
    private String descr;
    private Date createTime;
    private Integer uid;

    private String uname;
}
