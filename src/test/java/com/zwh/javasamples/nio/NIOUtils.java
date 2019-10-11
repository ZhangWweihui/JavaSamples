package com.zwh.javasamples.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author ZhangWeihui
 * @date 2019/9/27 17:10
 */
public class NIOUtils {

    public static String readFromChannel(SocketChannel socketChannel) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(256)) {
            ByteBuffer buf = ByteBuffer.allocate(256);
            int readBytes = socketChannel.read(buf);
            while (readBytes > 0) {
                buf.flip();
                baos.write(buf.array(), 0, buf.limit());
                buf.clear();
                readBytes = socketChannel.read(buf);
            }
            return new String(baos.toByteArray(), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
