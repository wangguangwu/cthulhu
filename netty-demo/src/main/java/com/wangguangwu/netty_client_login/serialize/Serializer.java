package com.wangguangwu.netty_client_login.serialize;

import com.wangguangwu.netty_client_login.serialize.impl.JsonSerializer;

/**
 * @author wangguangwu
 */
public interface Serializer {

    Serializer DEFAULT = new JsonSerializer();

    /**
     * 序列化算法
     *
     * @return byte
     */
    byte getSerializerAlgorithm();

    /**
     * java 对象转换为二进制
     *
     * @param object java object
     * @return binary data
     */
    byte[] serialize(Object object);


    /**
     * 二进制转化为 java 对象
     *
     * @param clazz 字节码
     * @param bytes 二进制数据
     * @param <T>   转化类型
     * @return java 对象
     */
    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
