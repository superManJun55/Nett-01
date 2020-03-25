package com.pcitc.nio.netty.inboundAndOutBoundHandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
/**
 *@ClassName       MyClientChannelInitializer
 *@Description:    TODO
 *@Author:         chen_wenjun
 *@CreateDate:     2020/2/26 17:39
 *@QQ              353376358
 *@Version:        V1.0
*/
public class MyClientChannelInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //出站：从尾部到头部的调用 MyClientHandler-->MyByteToLongEncoder
        pipeline.addLast(new MyByteToLongEncoder());
//        pipeline.addLast(new MyByteToLongDecoder());
        pipeline.addLast(new MyByteToLongDecoder2());

        pipeline.addLast(new MyClientHandler());
        System.out.println("MyClientChannelInitializer.....");
    }
}
