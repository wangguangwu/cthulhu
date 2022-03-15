package com.wangguangwu.client.http;

import lombok.Getter;
import lombok.Setter;


/**
 * Request class.
 *
 * @author wangguangwu
 */
@Getter
@Setter
public class Request {

    /**
     * url, such as https://www.baidu.com
     */
    public String url;

    /**
     * protocol, such as HTTP/1.1
     */
    private String protocol;

    /**
     * host, such as www.wangguangwu.com
     */
    private String host;

    /**
     * uri, such as "/"
     */
    private String uri;

    /**
     * port, such as 8080
     */
    private int port;

    /**
     * cookies
     */
    private String cookies;


}
