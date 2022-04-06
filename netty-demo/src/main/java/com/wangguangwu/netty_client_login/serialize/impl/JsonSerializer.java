package com.wangguangwu.netty_client_login.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.wangguangwu.netty_client_login.serialize.Serializer;
import com.wangguangwu.netty_client_login.serialize.SerializerAlgorithm;

/**
 * Implement serialization by using json.
 *
 * @author wangguangwu
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }

}
