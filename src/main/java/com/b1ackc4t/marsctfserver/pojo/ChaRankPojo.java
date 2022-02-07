package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class ChaRankPojo {
    private Integer uid;
    private String uname;
    private Date finishTime;
    @TableField(value = "`rank`")
    private Integer rank;
}
