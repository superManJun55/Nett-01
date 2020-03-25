package com.pcitc.nio.netty.inboundAndOutBoundHandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("客户端" + ctx.channel().remoteAddress() + " 发送的数据 ：" + msg);

//        System.out.println("服务器 发送数据");
//        ctx.writeAndFlush(54321L);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务器 发送数据");
        ctx.writeAndFlush(54321L);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Exception:" + cause.getMessage());
        ctx.close();
    }
}
