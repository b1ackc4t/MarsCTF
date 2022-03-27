package com.b1ackc4t.marsctfserver.util;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilTest {

    @Test
    void fun01() {
        Date d = new Date();
        d = new Date(d.getTime() + 10000000);
        System.out.println(d);

    }
}