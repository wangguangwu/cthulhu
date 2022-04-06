package com.wangguangwu.netty_protocol.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.wangguangwu.netty_protocol.serialize.Serializer;
import com.wangguangwu.netty_protocol.serialize.SerializerAlgorithm;

/**
 * json serialization.
 *
 * @author wangguangwu
 */
public class JsonSerializer implements Serializer {

    @Override
    public byte getSerialAlgorithm() {
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
