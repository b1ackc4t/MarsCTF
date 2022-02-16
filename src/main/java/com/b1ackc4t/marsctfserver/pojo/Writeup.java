package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Writeup {
    @TableId(type = IdType.AUTO)
    private Integer wid;
    private Integer cid;
    private Integer uid;
    private String title;
    private String text;
    private Boolean status;
    private Integer score;
    private Integer likes;
    private String wpTags;
    private String descr;
    private Date creTime;
    private Boolean done;
    private String comment;

    // 方便联合查询
    private String cname;
    private String uname;
    private String tname;

    public Writeup() {

    }

    public Writeup(Integer wid) {
        this.wid = wid;
    }
}
