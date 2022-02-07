package com.b1ackc4t.marsctfserver.util.generator;

import org.junit.jupiter.api.Test;

class SnowFlakeUtilTest {

    @Test
    void fun1() {

        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println((int) System.currentTimeMillis());
        System.out.println((int) System.currentTimeMillis());
        System.out.println((int) System.currentTimeMillis());
        ;
    }

    @Test
    void fun2() {
        System.out.println(SnowFlakeUtil.generatorUid());
        System.out.println(SnowFlakeUtil.generatorUid());
        System.out.println(SnowFlakeUtil.generatorUid());
    }

}