package com.wangguangwu.netty_client_login.server;

import com.wangguangwu.netty_client_login.protocol.Packet;
import com.wangguangwu.netty_client_login.protocol.PacketCodeC;
import com.wangguangwu.netty_client_login.protocol.request.LoginRequestPacket;
import com.wangguangwu.netty_client_login.protocol.response.LoginResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 * @author wangguangwu
 */
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        log.info("{} 客户端开始登陆", new Date());
        ByteBuf requestBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestBuf);

        if (packet instanceof LoginRequestPacket loginRequestPacket) {
            // login
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginRequestPacket.setVersion(packet.getVersion());
            if (valid(loginRequestPacket)) {
                loginResponsePacket.setSuccess(true);
                log.info("{} 登陆成功", new Date());
            } else {
                loginResponsePacket.setReason("账号密码校验失败");
                loginResponsePacket.setSuccess(false);
                log.info("{} 登陆失败", new Date());
            }
            // response
            ByteBuf responseBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
            ctx.channel().writeAndFlush(responseBuf);
        }

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return loginRequestPacket != null;
    }

}
