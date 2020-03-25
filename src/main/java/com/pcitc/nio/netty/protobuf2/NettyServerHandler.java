package com.pcitc.nio.netty.protobuf2;

import com.pcitc.nio.netty.protobuf.StudentPOJO;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;


public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        MyDataInfo.MyMessage message = (MyDataInfo.MyMessage) msg;
        if (message.getDateType() == MyDataInfo.MyMessage.DataType.StudentType) {
            MyDataInfo.Student student = message.getStudent();
            System.out.println(student.getInd() + "--->" + student.getName());
        } else if (message.getDateType() == MyDataInfo.MyMessage.DataType.WorkerType) {
            MyDataInfo.Worker worker = message.getWorker();
            System.out.println(worker.getSalary() + "--->" + worker.getName());
        } else {
            System.out.println("传输的类型不正确");
        }


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
        System.out.println("Exception:" + cause.getMessage());
    }
}
