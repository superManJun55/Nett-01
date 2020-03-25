package com.pcitc.nio.group;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

public class GroupChartClient {

    private final String HOST = "127.0.0.1";
    private final Integer PORT = 6677;
    private Selector selector;
    private SocketChannel channel;
    private String userName;

    public GroupChartClient() throws IOException {

        selector = Selector.open();

        channel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
        channel.configureBlocking(false);
        channel.register(selector, SelectionKey.OP_READ);
        userName = channel.getLocalAddress().toString().substring(1);
        System.out.println("客户端准备OK");

    }

    public void sendInfo(String msg) {
        msg = userName + " 说：" + msg;
        try {
            channel.write(ByteBuffer.wrap(msg.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readInfo() {
        try {
            int read = selector.select();
            if (read > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        ByteBuffer buf = ByteBuffer.allocate(1024);
                        socketChannel.read(buf);
                        buf.flip();
                        System.out.println(new String(buf.array()).trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        GroupChartClient client = new GroupChartClient();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    client.readInfo();
                    try {
                        Thread.currentThread().sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String msg = scanner.next();
            client.sendInfo(msg);
        }
    }
}
