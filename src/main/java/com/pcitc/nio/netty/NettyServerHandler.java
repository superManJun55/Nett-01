package com.pcitc.nio.netty;

import com.pcitc.nio.netty.protobuf.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println("客户端发送的数据：" + student.getId() + "--" + student.getName());


        //        super.channelRead(ctx, msg);
//        System.out.println("CTX = " + ctx);
//        System.out.println("线程：" + Thread.currentThread().getName());
//        ByteBuf buf = (ByteBuf) msg;
//
//        //加入定时任务
//        ctx.channel().eventLoop().schedule(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(2);
//                    ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,🐕", CharsetUtil.UTF_8));
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 5, TimeUnit.SECONDS);
//
//        Channel channel = ctx.channel();
//        ChannelPipeline pipeline = ctx.pipeline();
//        //接收客户端信息
//        System.out.println("客户端的地址是：" + channel.remoteAddress());
//        System.out.println("客户端发送的消息是：" + buf.toString(CharsetUtil.UTF_8));

    }

    //向客户端发送信息
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端🐕", CharsetUtil.UTF_8));
    }

    //有异常关闭即可
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        System.out.println(cause.getStackTrace());
    }
}
