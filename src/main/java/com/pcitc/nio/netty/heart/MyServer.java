package com.pcitc.nio.netty.heart;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Description: 测试心跳
 * @Author: chen_wenjun
 * @CreateDate: 2020/2/24 20:57
 * @UpdateUser: chen_wenjun
 * @UpdateDate: 2020/2/24 20:57
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class MyServer {
    public static void main(String[] args) throws Exception {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //处理空闲事件的处理器
                            //readerIdleTime：表示多长时间没有读，就会发送一个心跳检测包检查是否连接,
                            // writerIdleTime:表示多长时间没有写，就会发送一个心跳检测包检查是否连接,
                            // allIdleTime：表示多长时间没有读写，就会发送一个心跳检测包检查是否连接
                            pipeline.addLast(new IdleStateHandler(13, 5, 2, TimeUnit.SECONDS));

                            //处理空闲时间
                            pipeline.addLast(new MyServerHandler());

                        }
                    });

            ChannelFuture future = bootstrap.bind(7990).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
