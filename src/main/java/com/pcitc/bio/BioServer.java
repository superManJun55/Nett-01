package com.pcitc.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioServer {

    public static void main(String[] args) throws IOException {
        //线程池机制

        //1、创建一个线程池
        ExecutorService threadPool = Executors.newCachedThreadPool();
//        Executors.newSingleThreadExecutor();
//        Executors.newFixedThreadPool(1);
//        Executors.newScheduledThreadPool(1);
        //2、如果有客户链接，就创建一个线程
        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器启动成功...");

        while (true) {
            System.out.println("等待客户端连接...");
            final Socket socket = serverSocket.accept();
            System.out.println("连接一个Client:" + Thread.currentThread().getName());
            threadPool.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }

    }

    public static void handler(Socket socket) {
        try {
            byte[] bytes = new byte[1024];
            InputStream inputStream = socket.getInputStream();
            while (true) {
                System.out.println("B" + Thread.currentThread().getName());
                int len = inputStream.read(bytes);
                System.out.println("读取数据...");
                if (len != -1) {
                    System.out.println(new String(bytes, 0, len));
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("关闭连接。。。");
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
