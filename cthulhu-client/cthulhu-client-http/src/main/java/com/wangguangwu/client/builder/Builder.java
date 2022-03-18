package com.wangguangwu.client.builder;


import com.wangguangwu.client.http.Request;

/**
 * @author wangguangwu
 */
public interface Builder {

    /**
     * build url.
     *
     * @param url url
     * @return this
     */
    Builder url(String url);

    /**
     * build cookies.
     *
     * @param cookies cookies
     * @return this
     */
    Builder cookies(String cookies);

    /**
     * parse request.
     *
     * @return request
     */
    Request parse();

    /**
     * toString.
     *
     * @return string
     */
    @Override
    String toString();
}
