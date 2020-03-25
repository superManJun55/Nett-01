package com.pcitc.nio.netty.groupChat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {

    private static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    //channel处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【" + ctx.channel().remoteAddress() + "】" + dateTime + " 上线");
    }

    //channel处于非活动状态
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("【" + ctx.channel().remoteAddress() + "】" + dateTime + " 离线");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
//        channel.writeAndFlush("");

        System.out.println("客户端【" + channel.remoteAddress() + "】发送信息:" + msg);
        group.forEach(ch -> {
            if (channel != ch) {//给其他用户转发消息,排除自己
                ch.writeAndFlush("【" + channel.remoteAddress() + "】" + dateTime + " 发送消息：" + msg);
            } /*else {
                ch.writeAndFlush("【自己】" + dateTime + " 发送了消息：" + msg);
            }*/
        });

    }

    //连接
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        group.writeAndFlush("【客户端：" + channel.remoteAddress() + "】" + dateTime + "加入聊天");
        group.add(channel);
    }

    //断开连接
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
//        group.remove(channel);//不必手动删除，Group会自动删除
        group.writeAndFlush("【" + channel.remoteAddress() + "】" + dateTime + "离开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();//关闭通道即可
        System.out.println(" Netty Exception :" + cause.getMessage());
    }
}
