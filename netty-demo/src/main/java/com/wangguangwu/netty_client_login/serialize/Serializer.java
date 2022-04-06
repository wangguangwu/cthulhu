package com.wangguangwu.netty_client_login.serialize;

import com.wangguangwu.netty_client_login.serialize.impl.JsonSerializer;

/**
 * Serialization interface.
 *
 * @author wangguangwu
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * serialization algorithm
     *
     * @return byte
     */
    byte getSerializerAlgorithm();

    /**
     * convert java object to binary
     *
     * @param object java object
     * @return binary data
     */
    byte[] serialize(Object object);


    /**
     * convert binary to java object
     *
     * @param clazz clazz
     * @param bytes binary data
     * @param <T>   Class
     * @return java object
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
