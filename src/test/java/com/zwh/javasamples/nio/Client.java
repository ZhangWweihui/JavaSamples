package com.zwh.javasamples.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ZhangWeihui
 * @date 2019/9/27 11:56
 */
public class Client {

    public static void main(String[] args) throws IOException,InterruptedException {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        socketChannel.connect(new InetSocketAddress("localhost", 9191));
        //因为连接时异步的，这里要等连接完成
        while (!socketChannel.finishConnect()) {
            Thread.sleep(100);
            continue;
        }
        while (true) {
            int readyChannels = selector.select();
            if(readyChannels == 0) {
                Thread.sleep(500);
                continue;
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator iterator = selectedKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey sk = (SelectionKey) iterator.next();
                SocketChannel sc = (SocketChannel) sk.channel();
                if(sk.isConnectable()) {
                    System.out.println("a connection was established with a remote server.");
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    System.out.println("a channel is ready for reading.");
                    String receiptMsg = NIOUtils.readFromChannel(sc);
                    System.out.println("从服务器端读取到信息：" + receiptMsg);
                    String message = "Hello, server. " + System.currentTimeMillis();
                    ByteBuffer buf = ByteBuffer.wrap(message.getBytes());
                    while (buf.hasRemaining()) {
                        sc.write(buf);
                    }
                    sk.interestOps(SelectionKey.OP_READ);
                }
                iterator.remove();
            }
        }
    }
}
