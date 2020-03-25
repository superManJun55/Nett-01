package com.pcitc.nio.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

public class TestServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println("pipeline:" + ctx.pipeline() + " --channel:" + ctx.channel());
        if (msg instanceof HttpRequest) {

            System.out.println("pipeline hashcode = " + ctx.pipeline().hashCode() +
                    "; TestServerHandler hashcode = " + this.hashCode());

            HttpRequest httpRequest = (HttpRequest) msg;
            URI uri = new URI(httpRequest.getUri());
            if ("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求了favicon.ico资源,不做响应！");
                return;
            }
            System.out.println("msg的类型 = " + msg.getClass());
            System.out.println("客户端的地址 = " + ctx.channel().remoteAddress());


            ByteBuf content = Unpooled.copiedBuffer("服务器的响应信息", CharsetUtil.UTF_8);

            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                    HttpResponseStatus.OK, content);

            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(httpResponse);

        }
    }
}
