package com.b1ackc4t.marsctfserver.util;

import org.junit.jupiter.api.Test;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.*;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : b1ackc4t
 * @date : 2022/8/9 7:25
 */
class GraphicHelperTest {
    @Test
    void fun() throws FileNotFoundException, UnsupportedEncodingException {
        GraphicHelper graphicHelper = new GraphicHelper();
        FastByteArrayOutputStream fastByteArrayOutputStream = new FastByteArrayOutputStream();
        GraphicHelper.create(fastByteArrayOutputStream);
        System.out.println(new String(Base64.getEncoder().encode(fastByteArrayOutputStream.toByteArray())));
    }

}