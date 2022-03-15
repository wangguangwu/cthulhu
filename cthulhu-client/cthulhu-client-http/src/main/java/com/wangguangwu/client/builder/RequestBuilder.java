package com.wangguangwu.client.builder;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.wangguangwu.client.http.Request;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

import static com.wangguangwu.client.entity.Symbol.SLASH;

/**
 * @author wangguangwu
 */
@Slf4j
public class RequestBuilder implements Builder{

    private final Request request = new Request();

    @Override
    public  Builder url(String url) {
        request.setUrl(url);
        return this;
    }

    @Override
    public  Builder cookies(String cookies) {
        request.setCookies(cookies);
        return this;
    }

    @Override
    public Request parse() {
        try {
            URL urlObject = new URL(request.getUrl());
            String protocol = urlObject.getProtocol();
            String host = urlObject.getHost();
            int port = urlObject.getPort() != -1
                    ? urlObject.getPort() : urlObject.getDefaultPort();
            String uri = urlObject.getPath().startsWith(SLASH)
                    ? urlObject.getPath() : SLASH + urlObject.getPath();
            uri = StrUtil.isEmpty(urlObject.getQuery())
                    ? uri : uri + "?" + urlObject.getQuery();
            request.setProtocol(protocol);
            request.setHost(host);
            request.setPort(port);
            request.setUri(uri);
        } catch (IOException e) {
            log.error("Cthulhu parse error", e);
        }
        return request;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(request);
    }
}
