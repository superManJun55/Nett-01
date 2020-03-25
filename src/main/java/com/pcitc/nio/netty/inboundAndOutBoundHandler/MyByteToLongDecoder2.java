package com.pcitc.nio.netty.inboundAndOutBoundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 *@ClassName       MyByteToLongDecoder2
 *@Description:    解码器，不需要判断字节数。其中Void代表不需要状态管理
 *@Author:         chen_wenjun
 *@CreateDate:     2020/2/27 20:06
 *@QQ              353376358
 *@Version:        V1.0
*/
public class MyByteToLongDecoder2 extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder2");
        out.add(in.readLong());
    }
}
