package com.pcitc.nio.netty.buf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.ByteBuffer;

public class TestByteBuf {
    public static void main(String[] args) {

//        ByteBuf buf = Unpooled.buffer(10);
//        System.out.println("capacity=" + buf.capacity());
//        for (int i = 0; i < buf.capacity();i++){
//            buf.writeByte(i);
//        }
//        for (int i =0 ; i< buf.capacity(); i++){
//            System.out.println(buf.readByte());
//        }

        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,文君", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()){
            byte[] bytes = byteBuf.array();
            System.out.println(new String(bytes,CharsetUtil.UTF_8));
            System.out.println("byteBuf = " + byteBuf);


            System.out.println(byteBuf.arrayOffset());
            System.out.println(byteBuf.readerIndex());
            System.out.println(byteBuf.writerIndex());

            System.out.println(byteBuf.capacity());
            int len = byteBuf.readableBytes();
            System.out.println(len);
            System.out.println(byteBuf.getByte(0));//104 ASCII

            System.out.println("------------------------------");
            for (int i = 0;i < len; i++){
                System.out.println((char)byteBuf.getByte(i));
            }

            System.out.println(byteBuf.getCharSequence(2, 5, CharsetUtil.UTF_8));

        }


    }
}
