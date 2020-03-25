package com.pcitc.nio;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioFileChannelTest02 {
    public static void main(String[] args) throws  Exception {
        FileInputStream fis = new FileInputStream("1.txt");

        FileChannel channel = fis.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(20);

        channel.read(buffer);

        buffer.flip();

        System.out.println(new String(buffer.array(),0,buffer.limit()));

        channel.close();
        fis.close();
    }
}
