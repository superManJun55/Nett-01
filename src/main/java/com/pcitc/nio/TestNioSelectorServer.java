package com.pcitc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
/**
* @Description:    NIO服务器端
* @Author:         chen_wenjun
* @CreateDate:     2020/2/19 16:05
* @UpdateUser:     chen_wenjun
* @UpdateDate:     2020/2/19 16:05
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class TestNioSelectorServer {

    public static void main(String[] args) throws Exception {

        //1、创建channel
        ServerSocketChannel channel = ServerSocketChannel.open();
        //2、设置为非阻塞
        channel.configureBlocking(false);
        //3、绑定端口
        channel.bind(new InetSocketAddress(6667));
        //4、创建选择器
        Selector selector = Selector.open();
        //5、将channel绑定到selector上
        channel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {

            if (selector.select(1000) == 0){
                System.out.println("服务器等待一秒...");
                continue;
            }

            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                if (sk.isAcceptable()) {
                    System.out.println("连接一个Client..." + Thread.currentThread().getName());
                    SocketChannel accept = channel.accept();
                    accept.configureBlocking(false);
                    accept.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if (sk.isReadable()) {
                    System.out.println("Client有数据输入进来...." + Thread.currentThread().getName());
                    SocketChannel socketChannel = (SocketChannel) sk.channel();
//                    ByteBuffer dst = ByteBuffer.allocate(1024);
                    ByteBuffer dst = (ByteBuffer) sk.attachment();
                    int read = 0;
                    while ((read = socketChannel.read(dst)) > 0) {
                        dst.flip();
                        System.out.println(new String(dst.array(), 0, read));
                        dst.clear();
                    }
                }
            }

            //移除selectionKey,避免重复操作
            it.remove();
        }
    }
}
