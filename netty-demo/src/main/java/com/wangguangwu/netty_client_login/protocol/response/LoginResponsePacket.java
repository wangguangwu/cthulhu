package com.wangguangwu.netty_client_login.protocol.response;

import com.wangguangwu.netty_client_login.protocol.Packet;
import com.wangguangwu.netty_client_login.protocol.command.Command;
import lombok.Data;

/**
 * @author wangguangwu
 */
@Data
public class LoginResponsePacket extends Packet {

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
