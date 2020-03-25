package com.pcitc.nio.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Description: 模拟群聊天系统
 * @Author: chen_wenjun
 * @CreateDate: 2020/2/20 14:57
 * @UpdateUser: chen_wenjun
 * @UpdateDate: 2020/2/20 14:57
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class GroupChartServer {

    private final String HOST = "127.0.0.1";
    private final int PORT = 6677;
    private Selector selector;
    private ServerSocketChannel channel;

    public static void main(String[] args) {
        GroupChartServer server = new GroupChartServer();
        server.listen();

    }
    public GroupChartServer() {
        try {
            channel = ServerSocketChannel.open();
            channel.configureBlocking(false);
            channel.bind(new InetSocketAddress(HOST, PORT));
            selector = Selector.open();
            channel.register(selector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void listen() {

        try {
            while (true) {
                int timeCount = selector.select(2000);
                if (timeCount > 0) {
                    Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                    while (it.hasNext()) {
                        SelectionKey sk = it.next();
//                        SelectableChannel channel = sk.channel();
                        if (sk.isAcceptable()) {
                            SocketChannel accept = channel.accept();
                            accept.configureBlocking(false);
                            accept.register(selector, SelectionKey.OP_READ);
                            System.out.println(accept.getRemoteAddress() + ",上线了！");
                        }

                        if (sk.isReadable()) {
                            readData(sk);
                        }

                    }
                    it.remove();
                } else {
//                    System.out.println("连接等待中...");
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readData(SelectionKey key) {
        SocketChannel socketChannel = null;
        try {
            socketChannel = (SocketChannel) key.channel();
            ByteBuffer buf = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buf);
            if (count > 0) {
                String msg = new String(buf.array());
                System.out.println("收到" + socketChannel.getRemoteAddress() + "的消息【" + msg + "】");
                sendInfo2OtherListen(msg, socketChannel);
            }
        } catch (Exception e) {
            try {
                System.out.println(socketChannel.getRemoteAddress() + "离线了...");
                key.cancel();//移除注册
                socketChannel.close();//关闭通道
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }

    }

    public void sendInfo2OtherListen(String msg, SocketChannel selfChannel) throws IOException {
        System.out.println("服务器转发消息中....");
        for (SelectionKey key : selector.keys()) {
            Channel targetChannel = key.channel();
            //排除本身
            if (targetChannel instanceof SocketChannel && targetChannel != selfChannel) {
                SocketChannel socketChannel = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                socketChannel.write(buffer);

            }
        }

    }
}
