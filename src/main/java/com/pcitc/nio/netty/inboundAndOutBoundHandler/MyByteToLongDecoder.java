package com.pcitc.nio.netty.inboundAndOutBoundHandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
/**
 *@ClassName       MyByteToLongDecoder
 *@Description:    解码
 *@Author:         chen_wenjun
 *@CreateDate:     2020/2/26 17:21
 *@QQ              353376358
 *@Version:        V1.0
*/
public class MyByteToLongDecoder extends ByteToMessageDecoder {

    /**
     * @description
     * @author chen_wenjun
     * @date 2020/2/26 17:22
     * @param ctx 上下文对象
     * @param in  入站ByteBuf
     * @param out   List集合，将解码后的数据传输给下一个Handler
     * @return void
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("MyByteToLongDecoder");
        if (in.readableBytes() >= 8){
            out.add(in.readLong());
        }
    }
}
