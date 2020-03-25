package com.pcitc.nio.netty.inboundAndOutBoundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
/**
 *@ClassName       MyByteToLongEncoder
 *@Description:    编码
 *@Author:         chen_wenjun
 *@CreateDate:     2020/2/27 19:55
 *@QQ              353376358
 *@Version:        V1.0
*/
public class MyByteToLongEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("MyByteToLongEncoder");
        out.writeLong(msg);
    }
}
