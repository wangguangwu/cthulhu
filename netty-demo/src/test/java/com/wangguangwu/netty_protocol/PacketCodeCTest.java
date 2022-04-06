package com.wangguangwu.netty_protocol;


import com.wangguangwu.netty_protocol.protocol.LoginRequestPacket;
import com.wangguangwu.netty_protocol.protocol.Packet;
import com.wangguangwu.netty_protocol.protocol.PacketCodeC;
import com.wangguangwu.netty_protocol.serialize.Serializer;
import com.wangguangwu.netty_protocol.serialize.impl.JsonSerializer;
import io.netty.buffer.ByteBuf;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wangguangwu
 */
public class PacketCodeCTest {

    @Test
    public void test() {

        Serializer serializer = new JsonSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("wangguangwu");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        Assert.assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }

}
