package com.pcitc.nio.netty.inboundAndOutBoundHandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

public class MyClientHandler extends SimpleChannelInboundHandler<Long> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器端" + ctx.channel().remoteAddress() + " 发送的数据 ：" + msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client 发送数据");
        ctx.writeAndFlush(12345L);

//        ctx.writeAndFlush(Unpooled.copiedBuffer("sjsduendoslssddsa", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Exception:" + cause.getMessage());
        ctx.close();
    }
}
