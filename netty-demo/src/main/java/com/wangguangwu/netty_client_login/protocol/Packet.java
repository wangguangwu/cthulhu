package com.wangguangwu.netty_client_login.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

/**
 * @author wangguangwu
 */
@Data
public abstract class Packet {

    /**
     * protocol version
     */
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * get command
     *
     * @return command
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
