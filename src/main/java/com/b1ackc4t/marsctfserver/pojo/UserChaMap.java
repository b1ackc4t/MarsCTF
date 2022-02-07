package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
public class UserChaMap {
    private Integer uid;
    private Integer cid;
    private String status;
    private Date finishTime;
    @TableField(value = "`rank`")
    private Integer rank;

    public UserChaMap() {}
}
