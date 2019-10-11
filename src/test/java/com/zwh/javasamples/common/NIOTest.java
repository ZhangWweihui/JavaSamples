package com.zwh.javasamples.common;

import org.junit.Test;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SelectionKey;
import java.nio.file.StandardOpenOption;

/**
 * @author ZhangWeihui
 * @date 2019/9/26 11:21
 */
public class NIOTest {

    @Test
    public void testFileChannel() throws IOException {
        String filePath = ClassLoader.getSystemResource("article.txt").getFile();
        System.out.println(filePath);
        //RandomAccessFile raf = new RandomAccessFile("", "rw");
        FileChannel fileChannel = FileChannel.open(new File(filePath).toPath(), StandardOpenOption.READ);
        ByteBuffer buf = ByteBuffer.allocate(512);
        int bytesRead = fileChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("read " + bytesRead + " bytes");
            buf.flip();
            while (buf.hasRemaining()) {
                System.out.print((char)buf.get());
            }
            System.out.println();
            buf.clear();
            bytesRead = fileChannel.read(buf);
        }
        fileChannel.close();
    }

    @Test
    public void testScatteringReads() throws IOException {
        ByteBuffer buf1 = ByteBuffer.allocate(128);
        ByteBuffer buf2 = ByteBuffer.allocate(256);
        String filePath = ClassLoader.getSystemResource("article.txt").getFile();
        FileChannel fileChannel = FileChannel.open(new File(filePath).toPath(), StandardOpenOption.READ);
        fileChannel.read(new ByteBuffer[] {buf1, buf2});
        System.out.println("buf1 reads "+ buf1.position() +" bytes");
        System.out.println(new String(buf1.array()));
        System.out.println("buf2 reads "+ buf2.position() +" bytes");
        System.out.println(new String(buf2.array()));
    }

    @Test
    public void testGatheringWrite() throws IOException{
        ByteBuffer buf1 = ByteBuffer.allocate(10);
        buf1.put("ABCDEFGH".getBytes());
        ByteBuffer buf2 = ByteBuffer.allocate(10);
        buf2.put("12345678".getBytes());
        buf1.flip();
        buf2.flip();
        String filePath = ClassLoader.getSystemResource("test1.txt").getFile();
        FileChannel fileChannel = FileChannel.open(new File(filePath).toPath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);
        fileChannel.write(new ByteBuffer[]{buf1, buf2});
        fileChannel.close();
    }

    @Test
    public void testTransferFrom() throws IOException {
        String path1 = ClassLoader.getSystemResource("article.txt").getFile();
        FileChannel source = FileChannel.open(new File(path1).toPath(), StandardOpenOption.READ);

        String path2 = ClassLoader.getSystemResource("test1.txt").getFile();
        FileChannel target = FileChannel.open(new File(path2).toPath(), StandardOpenOption.WRITE, StandardOpenOption.APPEND);

        target.transferFrom(source, source.position(), source.size());

        source.close();
        target.close();
    }

    @Test
    public void testTransferTo() throws IOException {
        String path1 = ClassLoader.getSystemResource("article1.txt").getFile();
        FileChannel source = FileChannel.open(new File(path1).toPath(), StandardOpenOption.READ);

        String path2 = ClassLoader.getSystemResource("test1.txt").getFile();
        FileChannel target = FileChannel.open(new File(path2).toPath(), StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);

        source.transferTo(source.position(), source.size(), target);

        source.close();
        target.close();
    }

    @Test
    public void testSelectionKey() {
        int ops = SelectionKey.OP_READ | SelectionKey.OP_WRITE | SelectionKey.OP_CONNECT | SelectionKey.OP_ACCEPT ;
        System.out.println(new BigInteger(String.valueOf(ops)).toString(Character.MIN_RADIX));
    }
}
