package com.b1ackc4t.marsctfserver.util.generator;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShortUUIDTest {
    @Test
    void fun01() {
        Set set = new HashSet();
        for (int i = 0; i < 10000000; ++i) {
            set.add(ShortUUID.generateUid());
        }
    }

}