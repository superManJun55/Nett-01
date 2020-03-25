package com.pcitc.nio.netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
/**
* @Description:    模仿WEBSOCKET长连接
* @Author:         chen_wenjun
* @CreateDate:     2020/2/24 21:46
* @UpdateUser:     chen_wenjun
* @UpdateDate:     2020/2/24 21:46
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class WsServer {
    public static void main(String[] args) {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        try {
            bootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)//设置等待队列的大小
                    .handler(new LoggingHandler(LogLevel.INFO))//日志等级
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            //基于http协议，使用http编码解码
                            pipeline.addLast(new HttpServerCodec());
                            //是以块方式写，添加ChunkedWriteHandler处理器
                            pipeline.addLast(new ChunkedWriteHandler());
                            //http数据在传输过程中是分段的，HttpObjectAggregator可以将多个分段聚合
                            pipeline.addLast(new HttpObjectAggregator(8192));
                            //对应WebSocket，它是以帧的形式传输数据；将http协议升级为ws协议
                            pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));

                            pipeline.addLast(new WsServerHandler());
                            
                        }
                    });
            ChannelFuture future = bootstrap.bind(7000).sync();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
