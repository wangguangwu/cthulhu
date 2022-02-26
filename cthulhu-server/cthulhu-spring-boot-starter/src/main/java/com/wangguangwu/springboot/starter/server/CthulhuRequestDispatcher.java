package com.wangguangwu.springboot.starter.server;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * @author wangguangwu
 * @date 2022/2/26
 */
public class CthulhuRequestDispatcher implements RequestDispatcher {

    private final String resource;


    /**
     * Create a new CthulhuRequestDispatcher for the given resource.
     * @param resource the server resource to dispatch to, located at a
     * particular path or given by a particular name
     */
    public CthulhuRequestDispatcher(String resource) {
        Assert.notNull(resource, "Resource must not be null");
        this.resource = resource;
    }

    @Override
    public void forward(ServletRequest request, ServletResponse response) throws ServletException, IOException {

    }

    @Override
    public void include(ServletRequest request, ServletResponse response) throws ServletException, IOException {

    }
}
