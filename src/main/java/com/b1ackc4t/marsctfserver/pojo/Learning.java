package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Learning {
    @TableId(type = IdType.AUTO)
    private Integer lid;
    private String title;
    private String text;
    private Integer uid;
    private Integer tid;
    private Boolean hasTrain;
    private Date creTime;
    private String descr;

    private List<Integer> tgids;
    private List<Integer> cids;
    private String uname;
    private String tname;
    private List<String> tgnames;

    public Learning() {

    }

    public Learning(Integer lid) {
        this.lid = lid;
    }

}
