package com.itgowo.servercore;

import com.itgowo.socketframework.PackageMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

public class Test {
    public static void main(String[] args) {


        try {
//            SimpleServer.testSocketServer();
//            HttpClient.get("http://stzb.163.com/herolist/100006.html",null, new WebSocketServerHandler.onReceiveHandlerListener() {
//                @Override
//                public void onReceiveHandler(WebSocketServerHandler handler) {
//                    ServerManager.getLogger().info(handler.getBody(Charset.forName("gb2312")));
//                }
//
//                @Override
//                public void onError(Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            });
//            SimpleServer.testHttpServer();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        try {
//            Response response= HttpClient.RequestSync("localhost:16671",HttpMethod.POST,"asdfljasldf");
//            System.out.println(response.getBodaStr());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        HttpClient.RequestPOST("localhost:16671", "abacvasdfasewrjowriuwo", new onCallbackListener() {
//            @Override
//            public void onError(Response response, Exception e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onSuccess(Response response) {
//                System.out.println(response.getBodaStr());
//            }
//        });

        byte[] bytes = new byte[]{33, 22, 11, 44, 55, 66, 77, 88, 99, 11, 23, 34, 45, 56};
        ByteBuf byteBuf = Unpooled.buffer(40);
        byteBuf.writeBytes(bytes);
        PackageMessage packageMessage=PackageMessage.getPackageMessage();
        packageMessage.setType(PackageMessage.TYPE_DYNAMIC_LENGTH).setDataType(PackageMessage.DATA_TYPE_BYTE).setData(byteBuf);
       ByteBuf byteBuf1= packageMessage.encodePackageMessage();
        System.out.println("dddd");



        byte[] test1=new byte[]{121
                ,0,0,0,24,
                3,3,44,10,
                23,
                33, 22, 11, 44, 55, 66, 77, 88, 99, 11, 23, 34, 45, 56};
         PackageMessage.packageMessage(Unpooled.wrappedBuffer(test1));
    }

}
