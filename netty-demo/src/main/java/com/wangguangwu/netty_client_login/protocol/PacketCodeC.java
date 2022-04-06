package com.wangguangwu.netty_client_login.protocol;

import com.wangguangwu.netty_client_login.protocol.command.Command;
import com.wangguangwu.netty_client_login.protocol.request.LoginRequestPacket;
import com.wangguangwu.netty_client_login.protocol.response.LoginResponsePacket;
import com.wangguangwu.netty_client_login.serialize.Serializer;
import com.wangguangwu.netty_client_login.serialize.impl.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangguangwu
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;

    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> PACKET_TYPE_MAP;

    private final Map<Byte, Serializer> SERIALIZER_MAP;

    private PacketCodeC() {
        PACKET_TYPE_MAP = new HashMap<>();
        PACKET_TYPE_MAP.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        PACKET_TYPE_MAP.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        SERIALIZER_MAP = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        SERIALIZER_MAP.put(serializer.getSerializerAlgorithm(), serializer);
    }


}
