package com.itgowo.servercore.packagesocket;

import com.itgowo.servercore.ServerHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by lujianchao on 2018/5/17.
 */
public class PackageServerHandler implements ServerHandler {
    private ChannelHandlerContext ctx;
    private PackageMessage packageMessage;

    public PackageServerHandler(ChannelHandlerContext ctx, PackageMessage packageMessage) {
        this.ctx = ctx;
        this.packageMessage = packageMessage;
    }

    public PackageMessage getPackageMessage() {
        return packageMessage;
    }

    public PackageServerHandler setPackageMessage(PackageMessage packageMessage) {
        this.packageMessage = packageMessage;
        return this;
    }

    @Override
    public String toString() {
        return "PackageServerHandler{" +
                "ctx=" + ctx +
                ", packageMessage=" + packageMessage +
                '}';
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void sendData(byte[] data) {
        PackageMessage packageMessage=PackageMessage.getPackageMessage().setDataType(PackageMessage.DATA_TYPE_BYTE).setData(data);
        sendData(packageMessage);
    }
    public void sendData(PackageMessage data) {
        ctx.writeAndFlush(Unpooled.wrappedBuffer(data.encodePackageMessage().readableBytesArray()));
    }

}
