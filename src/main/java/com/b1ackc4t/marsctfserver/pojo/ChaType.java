package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ChaType {
    @TableId(type = IdType.AUTO)
    private Integer tid;
    private String tname;
}
