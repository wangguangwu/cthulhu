package com.wangguangwu.netty_protocol.protocol;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * java objects during communication.
 *
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
     * @return 指令
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();

}
