package com.wangguangwu.client.entity;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Commonly used field.
 *
 * @author wangguangwu
 */
@SuppressWarnings("unused")
public class Commons {

    /**
     * CONTENT_LENGTH
     */
    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * .com
     */
    public static final String COM = ".com";

    /**
     * robots.txt file.
     */
    public static final String ROBOTS = "/robots.txt";

    /**
     * User-agent: *.
     */
    public static final String USER_AGENT = "User-agent";

    /**
     * Disallow: /*?query=*.
     */
    public static final String DIS_ALLOW = "Disallow";

    /**
     * Allow: *.
     */
    public static final String ALLOW = "Allow";

    /**
     * default buffer size.
     */
    public static final int DEFAULT_BUFFER_SIZE = 1024;

    /**
     * default charset.
     */
    public static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

}
