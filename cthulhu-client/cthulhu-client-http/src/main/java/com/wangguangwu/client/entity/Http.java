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
            User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.51 Safari/537.36\r
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

    /**
     * zhipin cookie.
     */
    public static final String ZHIPIN_COOKIE = " Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1646832931,1646991992,1647332916,1647525901; wd_guid=a4990954-4fc4-4a08-96b3-79ef490a9935; historyState=state; lastCity=100010000; __g=-; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1647589860; acw_tc=0a099d8e16475898078851244e01a42585c7debb238fdc8ee05dd6b865d73f; __fid=b0f8fe2cf4f91f42b4eece11cff81795; __c=1647525900; __l=l=%2Fwww.zhipin.com%2Fjob_detail%2F%3Fquery%3Djava%26city%3D100010000%26industry%3D%26position%3D&r=&g=&s=3&friend_source=0&s=3&friend_source=0; __a=50749590.1647061808.1647332916.1647525900.17.3.4.17";

}
