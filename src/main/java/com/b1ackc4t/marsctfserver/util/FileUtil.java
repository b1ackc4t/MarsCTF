package com.b1ackc4t.marsctfserver.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
    /**
     * 无脑创建target文件，如果路径上文件夹不存在就新建，路径最后一个不管有没有后缀都视作文件来创建
     * @param target
     * @return 是否成功
     */
    public static boolean createDirAndFile(File target) {
        File parent = target.getParentFile();
        try {
            if (parent != null && !parent.exists()) {
                if(!parent.mkdirs()) throw new IOException("Folder creation failed.");
            }
            return target.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
