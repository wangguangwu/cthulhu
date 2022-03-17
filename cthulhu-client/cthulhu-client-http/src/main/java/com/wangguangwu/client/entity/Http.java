package com.wangguangwu.client.entity;

import java.util.Arrays;
import java.util.List;

/**
 * Http common.
 *
 * @author wangguangwu
 */
@SuppressWarnings("unused")
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
     * https version.
     */
    public static final String HTTPS_VERSION = "HTTP/1.1";

    /**
     * http version.
     */
    public static final String HTTP_VERSION = "HTTP/1.0";

    /**
     * http protocol start.
     */
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
    public static final List<Integer> BAD_RESPONSE = Arrays.asList(401, 403, 404);

    /**
     * moved response status code.
     */
    public static final List<Integer> MOVED_RESPONSE = Arrays.asList(301, 302);

    /**
     * location:
     */
    public static final String LOCATION = "location";

    /**
     * send request template.
     */
    public static final String REQUEST_TEMPLATE = """
            User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10.15; rv:97.0) Gecko/20100101 Firefox/97.0\r
            Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,*/*;q=0.8\r
            Accept-Language: en-US,en;q=0.5\r
            Connection: Keep-Alive\r
            Upgrade-Insecure-Requests: 1\r
            Sec-Fetch-Dest: document\r
            Sec-Fetch-Mode: navigate\r
            Sec-Fetch-Site: none\r
            Sec-Fetch-User: ?1\r
            \r
            """;

}
