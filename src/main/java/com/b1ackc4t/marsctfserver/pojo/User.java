package com.b1ackc4t.marsctfserver.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.b1ackc4t.marsctfserver.util.generator.ShortUUID;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

@Data
public class User {

    @TableId
    private Integer uid;
    private String uname;
    @JSONField(serialize=false)
    private String upassword;
    private String role;
    private Boolean sex;
    private String skill;
    private String email;
    private String unit;
    private String sign;
    private Date regTime;
    private Integer web;
    private Integer pwn;
    private Integer crypto;
    private Integer misc;
    private Integer re;
    private Integer other;
    private Integer score;


    public User() {}

    public User(Integer uid) {
        this.uid = uid;
    }

    public User(String uname, String upassword) {
        this.uname = uname;
        this.upassword = upassword;
    }

}
