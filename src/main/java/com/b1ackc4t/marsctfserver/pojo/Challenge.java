package com.b1ackc4t.marsctfserver.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Challenge {
    @TableId(type = IdType.AUTO)
    private Integer cid;
    private String cname;
    private String descr;
    private Integer score;
//    @JSONField(serialize=false)
//    private Integer tid;
    private Boolean exposed;
    private Date cretime;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private Integer fid;
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String fname;
    private Integer tid;
    private String tname;
    private String color;
    private String flag;
    private List<Integer> tags;
    private List<String> tagsView;
    private Integer finishedNum;
    private String status;
    private Integer masterUid;
    private String masterName;

    public Challenge() {

    }

    public Challenge(Integer cid) {
        this.cid = cid;
    }

    public Challenge briefView() {
        descr = null;
        score = null;
        exposed = null;
        cretime = null;
        fid = null;
        flag = null;
        return this;
    }

    public Challenge simpleView() {
        exposed = null;
        cretime = null;
        fid = null;
        flag = null;
        return this;
    }
}
