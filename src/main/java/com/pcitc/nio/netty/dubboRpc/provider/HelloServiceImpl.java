package com.pcitc.nio.netty.dubboRpc.provider;

import com.pcitc.nio.netty.dubboRpc.publicInterface.HelloService;
import io.netty.util.internal.StringUtil;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {

        System.out.println("收到客户端消息：" + msg);

        if (!StringUtil.isNullOrEmpty(msg)){
            return "你好，客户端，收到你的信息[" + msg + "]";
        }
        return "None Info";
    }
}
