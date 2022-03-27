package com.b1ackc4t.marsctfserver.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.b1ackc4t.marsctfserver.util.generator.ShortUUID;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private String title;
    private Integer level;
    private Integer rank;


    public User() {}

    public User(Integer uid) {
        this.uid = uid;
    }

    public User(String uname, String upassword) {
        this.uname = uname;
        this.upassword = upassword;
    }

    public void setRankTitle() {
        List<Title> titles = new ArrayList<>();
        titles.add(new Title("小白-Ⅴ", 0, 0, 100));
        titles.add(new Title("小白-Ⅳ", 0, 100, 200));
        titles.add(new Title("小白-Ⅲ", 0, 200, 300));
        titles.add(new Title("小白-Ⅲ", 0, 300, 400));
        titles.add(new Title("小白-Ⅱ", 0, 400, 500));
        titles.add(new Title("小白-Ⅰ", 0, 500, 800));
        titles.add(new Title("脚本小子-Ⅲ", 1, 800, 1100));
        titles.add(new Title("脚本小子-Ⅱ", 1, 1100, 1400));
        titles.add(new Title("脚本小子-Ⅰ", 1, 1400, 2000));
        titles.add(new Title("脚本选手-Ⅲ", 1, 2000, 2400));
        titles.add(new Title("脚本选手-Ⅱ", 1, 2400, 2800));
        titles.add(new Title("脚本选手-Ⅰ", 1, 2800, 3600));
        titles.add(new Title("脚本高手-Ⅲ", 1, 3600, 4100));
        titles.add(new Title("脚本高手-Ⅱ", 1, 4100, 4600));
        titles.add(new Title("脚本高手-Ⅰ", 1, 4600, 5600));
        titles.add(new Title("工程师-Ⅲ", 2, 5600, 6600));
        titles.add(new Title("工程师-Ⅱ", 2, 6600, 8100));
        titles.add(new Title("工程师-Ⅰ", 2, 8100, 10000));
        titles.add(new Title("研究员-Ⅲ", 3, 10000, 12000));
        titles.add(new Title("研究员-Ⅱ", 3, 12000, 15000));
        titles.add(new Title("研究员-Ⅰ", 3, 15000, 20000));
        titles.add(new Title("黑客-Ⅲ", 4, 20000, 25000));
        titles.add(new Title("黑客-Ⅱ", 4, 25000, 35000));
        titles.add(new Title("黑客-Ⅰ", 4, 35000, 50000));
        titles.add(new Title("极客-Ⅲ", 5, 50000, 60000));
        titles.add(new Title("极客-Ⅱ", 5, 70000, 80000));
        titles.add(new Title("极客-Ⅰ", 5, 80000, 100000));
        titles.add(new Title("飞升者", 6, 100000, Integer.MAX_VALUE));

        for (Title title : titles) {
            if (score >= title.getMaxScore()) {
            } else {
                this.title = title.getTitle();
                this.level = title.getLevel();
                break;
            }
        }

    }

}
