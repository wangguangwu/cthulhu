package com.wangguangwu.configuration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.wangguangwu.server.CthulhuServletWebServerFactory;
import com.wangguangwu.server.CthulhuServletWebServerFactoryCustomizer;

/**
 * @author yuanzhixiang
 */
@Configuration
public class WebServerConfiguration {

    @Bean
    CthulhuServletWebServerFactory cthulhuServletWebServerFactory(
        ObjectProvider<UndertowDeploymentInfoCustomizer> deploymentInfoCustomizers,
        ObjectProvider<UndertowBuilderCustomizer> builderCustomizers) {
        return new CthulhuServletWebServerFactory();
    }

    @Bean
    CthulhuServletWebServerFactoryCustomizer cthulhuServletWebServerFactoryCustomizer(
        ServerProperties serverProperties) {
        return new CthulhuServletWebServerFactoryCustomizer();
    }
}
