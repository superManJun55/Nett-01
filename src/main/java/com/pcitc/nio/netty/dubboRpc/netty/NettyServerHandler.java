package com.pcitc.nio.netty.dubboRpc.netty;

import com.pcitc.nio.netty.dubboRpc.provider.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg = " + msg);

        HelloServiceImpl helloService = new HelloServiceImpl();
        String message = helloService.hello("Hello World");
        ctx.writeAndFlush(message);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Exception = " + cause.getMessage());
        ctx.close();
    }
}
