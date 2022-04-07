package com.wangguangwu.decorator.service.impl;

import com.wangguangwu.decorator.service.Shape;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class Circle implements Shape {

    @Override
    public void draw() {
     log.info("Shape: Circle");
    }
}
