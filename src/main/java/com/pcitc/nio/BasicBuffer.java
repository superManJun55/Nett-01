package com.pcitc.nio;

import java.nio.IntBuffer;

public class BasicBuffer {

    public static void main(String[] args) {
        //Ctrl+Alt+V  代码赋值

        //1、创建Buffer
        IntBuffer intBuffer = IntBuffer.allocate(10);

        //2、赋值
        for (int i = 0; i < intBuffer.capacity(); i++) {
            intBuffer.put( i * 2);
        }

        //3、切换为读操作
        intBuffer.flip();


        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
//            System.out.println(intBuffer.remaining());
        }
    }
}
