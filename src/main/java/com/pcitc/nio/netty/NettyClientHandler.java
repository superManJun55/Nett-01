package com.pcitc.nio.netty;

import com.pcitc.nio.netty.protobuf.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当管道就绪就会触发的方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        System.out.println("Client CTX = " + ctx);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务器端🐱", CharsetUtil.UTF_8));

        StudentPOJO.Student wenJun = StudentPOJO.Student.newBuilder().setId(1).setName("WenJun").build();
        ctx.writeAndFlush(wenJun);

    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回复的信息： " + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器的地址是：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(cause.getMessage());
        ctx.close();
    }
}
