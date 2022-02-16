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

    // 方便查询
    private String cname;
    private String tname;
    private Integer score;
    public UserChaMap() {}
}
