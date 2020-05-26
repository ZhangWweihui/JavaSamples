package com.zwh.utils;

import org.springframework.util.Assert;

import java.io.*;

/**
 * @author ZhangWeihui
 * @date 2019/8/1 16:59
 */
public class FileUtils {

    public static ByteArrayOutputStream readFile(String filePath) throws IOException {
        Assert.hasText(filePath, "文件路径不能为空");
        BufferedInputStream bis  = new BufferedInputStream(new FileInputStream(filePath));
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes = new byte[1024];
        int read = 0;
        while((read = bis.read(bytes))>0){
            baos.write(bytes, 0, bytes.length);
        }
        return baos;
    }
}
