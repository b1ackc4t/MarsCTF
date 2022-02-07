package com.b1ackc4t.marsctfserver.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MdUtilTest {
    @Test
    void fun01() {
        String md = "# fewafwe";
        System.out.println(MdUtil.mdToText(md));
        System.out.println(MdUtil.getDescrByMd(md));
    }
}