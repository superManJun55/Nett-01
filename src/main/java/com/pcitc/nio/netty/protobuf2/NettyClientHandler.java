package com.pcitc.nio.netty.protobuf2;

import com.pcitc.nio.netty.protobuf.StudentPOJO;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.Random;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //当管道就绪就会触发的方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {


        int random = new Random().nextInt(3);
        MyDataInfo.MyMessage message = null;
        if (0 == random) {
            MyDataInfo.MyMessage.DataType studentType = MyDataInfo.MyMessage.DataType.StudentType;
            MyDataInfo.Student student = MyDataInfo.Student.newBuilder().setInd(1).setName("wenJun").build();
            message = MyDataInfo.MyMessage.newBuilder().setDateType(studentType).setStudent(student).build();
        } else {
            MyDataInfo.MyMessage.DataType workerType = MyDataInfo.MyMessage.DataType.WorkerType;
            MyDataInfo.Worker worker = MyDataInfo.Worker.newBuilder().setSalary(5500f).setName("CHEN").build();
            message = MyDataInfo.MyMessage.newBuilder().setDateType(workerType).setWorker(worker).build();
        }

        ctx.writeAndFlush(message);

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
