package com.pcitc.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

// ccmt：类注释， mcmt：方法注释

public class NioFileChannelTest01 {
    public static void main(String[] args) throws Exception {

        String str = "hello,陈文军";//一个汉字占用3个字节, 共15个字节
        FileOutputStream fos = new FileOutputStream("1.txt");

        FileChannel channel = fos.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.put(str.getBytes());

        buffer.flip();

        channel.write(buffer);

        channel.close();
        fos.close();

    }

}
