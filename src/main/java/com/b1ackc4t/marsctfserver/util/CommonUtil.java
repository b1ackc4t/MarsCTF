package com.b1ackc4t.marsctfserver.util;

import java.io.IOException;
import java.net.ServerSocket;

public class CommonUtil {
    public static void main(String[] args) throws IOException {
        System.out.println(1==new Integer(2));
    }

    /**
     * 返回本机的一个可用端口[min,max)
     * @param min
     * @param max
     * @return
     */
    public static int getAvailableTcpPort(int min, int max) {
        // 指定范围10000到65535
        for (int i = min; i < max; i++) {
            try {
                new ServerSocket(i).close();
                return i;
            } catch (IOException e) { // 抛出异常表示不可以，则进行下一个
                continue;
            }
        }
        return -1;
    }
}
