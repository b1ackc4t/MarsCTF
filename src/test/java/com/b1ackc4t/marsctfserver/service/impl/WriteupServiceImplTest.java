package com.b1ackc4t.marsctfserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.b1ackc4t.marsctfserver.pojo.Writeup;
import com.b1ackc4t.marsctfserver.service.WriteupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class WriteupServiceImplTest {
    @Autowired
    WriteupService writeupService;
    @Test
    void fun01() {
        Writeup writeup = new Writeup();
        writeup.setCid(41);
        writeup.setUid(1241551);
        writeup.setTitle("fwafaf");
        writeup.setText("wwwwwwwwwwwwwwwwwwwwwwwwwwww");
        writeup.setWpTags("fwafg gwagawdf fawfgawfg");
        writeupService.saveWriteup(writeup);
    }

    @Test
    void fun02() {
        Writeup writeup = new Writeup();
        writeup.setCid(41);
        writeup.setUid(1241551);
        writeup.setTitle("fwafaf");
        writeup.setText("wwwwwwwwwwwwwwwwwwwwwwwwwwww");
        writeup.setWpTags("fwafg gwagawdf fawfgawfg");
        System.out.println(JSONObject.toJSONString(writeup));
    }
}