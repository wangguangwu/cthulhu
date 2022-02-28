package com.wangguangwu.entity;

import lombok.Data;

/**
 * A Cthulhu Object.
 *
 * @author wangguangwu
 * @date 2022/2/27
 */
@Data
public class CthulhuVO {

    private String name;

    private Integer age;

    public CthulhuVO(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
