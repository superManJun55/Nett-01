package com.pcitc.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
/**
* @Description:    NIO客户端
* @Author:         chen_wenjun
* @CreateDate:     2020/2/19 16:00
* @UpdateUser:     chen_wenjun
* @UpdateDate:     2020/2/19 16:00
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class TestNioSelectorClient {
    public static void main(String[] args) throws Exception {
        SocketChannel channel = SocketChannel.open(new InetSocketAddress("localhost", 6667));

        channel.configureBlocking(false);

        ByteBuffer buf = ByteBuffer.allocate(1024);

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            String str = scanner.next();
            buf.put((LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " :\t" + str).getBytes());
            buf.flip();
            channel.write(buf);
            buf.clear();
        }
    }

}
