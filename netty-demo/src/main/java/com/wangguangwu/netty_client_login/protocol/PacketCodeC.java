package com.wangguangwu.netty_client_login.protocol;

import com.wangguangwu.netty_client_login.protocol.command.Command;
import com.wangguangwu.netty_client_login.protocol.request.LoginRequestPacket;
import com.wangguangwu.netty_client_login.protocol.response.LoginResponsePacket;
import com.wangguangwu.netty_client_login.serialize.Serializer;
import com.wangguangwu.netty_client_login.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangguangwu
 */
public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Command.LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(Command.LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    /**
     * encode
     *
     * @param byteBufAllocator byteBufAllocator
     * @param packet           packet
     * @return byteBuf
     */
    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        // 1. create ByteBuf object
        ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 2. serialize java objects
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. encode
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf) {
        // skip MAGIC_NUMBER
        byteBuf.skipBytes(4);

        // skip version
        byteBuf.skipBytes(1);

        // serialize algorithm
        byte serializeAlgorithm = byteBuf.readByte();

        // command
        byte command = byteBuf.readByte();

        // data length
        int length = byteBuf.readInt();

        byte[] bytes  = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }

}
