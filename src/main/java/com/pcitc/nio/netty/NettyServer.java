package com.pcitc.nio.netty;

import com.pcitc.nio.netty.protobuf.StudentPOJO;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufDecoderNano;

public class NettyServer {

    public static void main(String[] args) {

        //NioServerLoopGroup是无限循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            //创建服务器端的启动对象，配置参数
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workerGroup)//设置两个线程组
                    .channel(NioServerSocketChannel.class)//使用NioServerSocketChannel，作为服务器的通道实现
                    .option(ChannelOption.SO_BACKLOG, 128)//设置线程队列得到的连接个数
                    .childOption(ChannelOption.SO_KEEPALIVE, true)//设置活动连接状态
                    .childHandler(new ChannelInitializer<SocketChannel>() {//创建一个通道测试对象
                                @Override
                                protected void initChannel(SocketChannel ch) throws Exception {
                                    ChannelPipeline pipeline = ch.pipeline();
                                    pipeline.addLast("decoder",new ProtobufDecoder(StudentPOJO.Student.getDefaultInstance()));
                                    pipeline.addLast(new NettyServerHandler());
                                }
                            });


            System.out.println("SERVER IS READY!");

            //绑定一个端口，生成一个ChannelFuture
            //启动服务器
            ChannelFuture chf = bootstrap.bind(6668).sync();

            chf.addListener(
                    (ChannelFutureListener) f -> {
                        if (chf.isSuccess()) System.out.println("绑定端口6668成功！");
                    }
               /*new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (chf.isSuccess()) System.out.println("绑定端口6668成功！");
                }
            }*/);

            //对关闭通道进行监听
            chf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {

            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }


    }
}
