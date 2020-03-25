package com.pcitc.nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class TestMapByteBuffer {
    public static void main(String[] args) throws  Exception {

//        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);
//
//        FileChannel outChannel = FileChannel.open(Paths.get("4.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        RandomAccessFile raf = new RandomAccessFile("2.txt", "rw");

        FileChannel channel = raf.getChannel();

        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte) 'A');
        mappedByteBuffer.put(3, (byte)'B');

//        mappedByteBuffer.put(5,(byte)'Y');//索引从0开始，直到4

        channel.close();
        raf.close();
    }
}
