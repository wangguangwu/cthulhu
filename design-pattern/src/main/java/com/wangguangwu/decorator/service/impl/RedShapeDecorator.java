package com.wangguangwu.decorator.service.impl;

import com.wangguangwu.decorator.service.Shape;
import com.wangguangwu.decorator.service.ShapeDecorator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class RedShapeDecorator extends ShapeDecorator {

    public RedShapeDecorator(Shape decoratedShape) {
        super(decoratedShape);
    }

    @Override
    public void draw() {
        decoratedShape.draw();
        setRedBorder(decoratedShape);
    }

    private void setRedBorder(Shape decoratedShape) {
        log.info("{} Border Color: Red", decoratedShape);
    }

}
