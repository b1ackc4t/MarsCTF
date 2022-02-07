package com.b1ackc4t.marsctfserver.util.generator;

import java.util.UUID;

public class ShortUUID {
    public static int generateUid() {
        UUID ID = UUID.randomUUID();
        return Math.abs(ID.hashCode());
    }

    public static void main(String[] args) {
        System.out.println(UUID.randomUUID());
    }
}
