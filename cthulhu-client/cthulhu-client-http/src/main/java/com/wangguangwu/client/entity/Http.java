package com.wangguangwu.client.entity;

import java.util.List;

/**
 * Http common.
 *
 * @author wangguangwu
 */
public class Http {

    /**
     * http protocol port.
     */

    public static final int HTTP_PORT = 80;

    /**
     * https protocol port.
     */

    public static final int HTTPS_PORT = 443;

    /**
     * http protocol.
     */
    public static final String HTTP_PROTOCOL = "http";

    /**
     * https protocol.
     */
    public static final String HTTPS_PROTOCOL = "https";

    /**
     * http protocol start.
     */
    @SuppressWarnings({"unused"})
    public static final String HTTP_PROTOCOL_START = "http://";

    /**
     * https protocol start.
     */
    public static final String HTTPS_PROTOCOL_START = "https://";

    /**
     * "Content-Type".
     */
    public static final String CONTENT_TYPE = "Content-Type";

    /**
     * "charset=".
     */
    public static final String CHARSET = "charset=";

    /**
     * "Content-Length".
     */

    public static final String CONTENT_LENGTH = "Content-Length";

    /**
     * bad response status code.
     */
    public static final List<Integer> BAD_RESPONSE = List.of(401, 403, 404);

    /**
     * moved response status code.
     */
    public static final List<Integer> MOVED_RESPONSE = List.of(301, 302);

    public static final String LOCATION = "location";

}
