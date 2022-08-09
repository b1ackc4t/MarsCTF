package com.b1ackc4t.marsctfserver.util;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilTest {

    @Test
    void fun01() {
        Date d = new Date();
        d = new Date(d.getTime() + 10000000);
        System.out.println(d);

    }

    @Test
    void fun02() {
        Pattern r = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_\\u4e00-\\u9fa5]+$");
        Matcher m = r.matcher("w");

        System.out.println("matches(): "+m.matches());
    }
}