package com.zwh.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @author ZhangWeihui
 * @date 2019/9/24 16:27
 */
public class MyClassLoader extends ClassLoader {

    public MyClassLoader() {}

    public MyClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected Class<?> findClass(String name) {
        File file = getClassFile(name);
        try {
            byte[] bytes = getClassBytes(file);
            Class<?> clazz = super.defineClass(name, bytes, 0, bytes.length);
            return clazz;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private File getClassFile(String name) {
        String className = name.substring(name.lastIndexOf(".")+1);
        return new File("F:\\" + className + ".class");
    }

    private byte[] getClassBytes(File file) {
        try(FileInputStream fis = new FileInputStream(file);
            ReadableByteChannel rbc = Channels.newChannel(fis);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            WritableByteChannel wbc = Channels.newChannel(baos)){

            ByteBuffer buf = ByteBuffer.allocate(1024);
            while (rbc.read(buf) >= 0 || buf.position() != 0) {
                buf.flip();
                wbc.write(buf);
                buf.compact();
            }
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
