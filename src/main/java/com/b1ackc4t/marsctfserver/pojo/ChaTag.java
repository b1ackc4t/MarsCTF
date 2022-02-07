package com.b1ackc4t.marsctfserver.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class ChaTag {
    @TableId(type = IdType.AUTO)
    private Integer tgid;
    private String tgname;
    private String tname;
}
