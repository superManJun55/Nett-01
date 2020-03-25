package com.pcitc.nio.netty.inboundAndOutBoundHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();


        //入站，解码：从头部到尾部的调用 MyByteToLongDecoder-->MyServerHandler
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());

        //出站，编码
        pipeline.addLast(new MyByteToLongEncoder());
        pipeline.addLast(new MyServerHandler());
        System.out.println("MyServerChannelInitializer......");

    }
}
