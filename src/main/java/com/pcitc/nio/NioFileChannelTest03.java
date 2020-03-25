package com.pcitc.nio;

import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
/**
* @Description:    java类作用描述
* @Author:         chen_wenjun
* @CreateDate:     2020/2/18 18:47
* @UpdateUser:     chen_wenjun
* @UpdateDate:     2020/2/18 18:47
* @UpdateRemark:   修改内容
* @Version:        1.0
*/
public class NioFileChannelTest03 {

    public static void main(String[] args)  throws  Exception{

        FileChannel inChannel = FileChannel.open(Paths.get("1.txt"), StandardOpenOption.READ);

        FileChannel outChannel = FileChannel.open(Paths.get("2.txt"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

//        inChannel.transferTo(0, inChannel.size(), outChannel);

        outChannel.transferFrom(inChannel, 0, inChannel.size());

        outChannel.close();

        inChannel.close();


    }
}
