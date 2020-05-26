package com.zwh.javasamples.nio;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author ZhangWeihui
 * @date 2019/9/27 15:44
 */
public class Server {

    public static void main(String[] args) throws IOException,InterruptedException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9191));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
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
                if(sk.isAcceptable()) {
                    System.out.println("Server : a connection was accepted by a ServerSocketChannel.");
                    ServerSocketChannel ssc = (ServerSocketChannel) sk.channel();
                    SocketChannel sc = ssc.accept();
                    String message = "I accept you. " + System.currentTimeMillis();
                    ByteBuffer buf = ByteBuffer.wrap(message.getBytes());
                    // 注意SocketChannel.write()方法的调用是在一个while循环中的。Write()方法无法保证能写多少字节到SocketChannel。所以，我们重复调用write()直到Buffer没有要写的字节为止。
                    while (buf.hasRemaining()) {
                        sc.write(buf);
                    }
                    sc.configureBlocking(false);
                    sc.register(selector, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    SocketChannel sc = (SocketChannel) sk.channel();
                    System.out.println("Server : a channel is ready for reading.");
                    String receiptMsg = NIOUtils.readFromChannel(sc);
                    System.out.println("从客户端读取到信息：" + receiptMsg);
                    String message = "回复给客户端的信息：The backlog argument is the requested maximum number of pending connections on the socket. " + System.currentTimeMillis();
                    ByteBuffer buf = ByteBuffer.wrap(message.getBytes("UTF-8"));
                    while(buf.hasRemaining()) {
                        sc.write(buf);
                    }
                    sk.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
                }
                iterator.remove();
            }
        }
    }
}
