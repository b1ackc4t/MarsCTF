package com.b1ackc4t.marsctfserver.pojo;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChaTagTest {

    @Test
    void fun01() {
        ChaTag chaTag = new ChaTag();
        chaTag.setTgname("文件包含");
        chaTag.setTname("web");
        System.out.println(JSON.toJSONString(chaTag));
    }

    @Test
    void fun02() {
        System.out.println(new PageInfo<>(null));
    }

}