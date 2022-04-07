package com.wangguangwu.decorator.service;


/**
 * 实现了 Shape 接口的抽象装饰类
 *
 * @author wangguangwu
 */
public class ShapeDecorator implements Shape {

    protected Shape decoratedShape;

    public ShapeDecorator(Shape decoratedShape) {
        this.decoratedShape = decoratedShape;
    }

    @Override
    public void draw() {
        decoratedShape.draw();
    }
}
