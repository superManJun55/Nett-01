package com.pcitc.nio.netty.dubboRpc.provider;

import com.pcitc.nio.netty.dubboRpc.netty.NettyServer;

public class ServerBootStrap {

    public static void main(String[] args) {

        NettyServer.startServer("localhost", 8768);
    }
}
