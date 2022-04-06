package com.wangguangwu.netty_protocol.serialize;

import com.wangguangwu.netty_protocol.serialize.impl.JsonSerializer;

/**
 * serial rules.
 *
 * @author wangguangwu
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * serial algorithm
     *
     * @return byte
     */
    byte getSerialAlgorithm();

    /**
     * convert java object to binary
     *
     * @param object java object
     * @return binary data
     */
    byte[] serialize(Object object);

    /**
     * binary convert to java object
     *
     * @param T     generics
     * @param bytes binary data
     * @param <T>   generics
     * @return java object after convert
     */
    <T> T deserialize(Class<T> T, byte[] bytes);
}
