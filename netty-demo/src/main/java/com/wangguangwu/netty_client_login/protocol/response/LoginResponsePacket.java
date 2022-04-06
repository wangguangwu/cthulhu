package com.wangguangwu.netty_client_login.protocol.response;

import com.wangguangwu.netty_client_login.protocol.Packet;
import com.wangguangwu.netty_client_login.protocol.command.Command;
import lombok.Data;

/**
 * @author wangguangwu
 */
@Data
public class LoginResponsePacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
