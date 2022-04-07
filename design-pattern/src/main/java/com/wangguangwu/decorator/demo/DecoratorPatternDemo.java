package com.wangguangwu.decorator.demo;

import com.wangguangwu.decorator.service.Shape;
import com.wangguangwu.decorator.service.ShapeDecorator;
import com.wangguangwu.decorator.service.impl.Circle;
import com.wangguangwu.decorator.service.impl.Rectangle;
import com.wangguangwu.decorator.service.impl.RedShapeDecorator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author wangguangwu
 */
@Slf4j
public class DecoratorPatternDemo {

    public static void main(String[] args) {
        Shape circle = new Circle();
        ShapeDecorator redCircle = new RedShapeDecorator(new Circle());
        ShapeDecorator redRectangle = new RedShapeDecorator(new Rectangle());
        log.info("Circle with normal border");
        circle.draw();

        log.info("Circle of red border");
        redCircle.draw();

        log.info("Rectangle of red border");
        redRectangle.draw();
    }

}
