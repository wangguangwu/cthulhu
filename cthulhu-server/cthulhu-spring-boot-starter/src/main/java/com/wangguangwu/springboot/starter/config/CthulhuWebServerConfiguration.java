package com.wangguangwu.springboot.starter.config;

import com.wangguangwu.springboot.starter.server.CthulhuServletWebServerFactory;
import com.wangguangwu.springboot.starter.server.CthulhuServletWebServerFactoryCustomizer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.context.annotation.Bean;

/**
 * @author wangguangwu
 */
public class CthulhuWebServerConfiguration {

    @Bean
    @SuppressWarnings("unused")
    CthulhuServletWebServerFactory cthulhuServletWebServerFactory(
            ObjectProvider<UndertowDeploymentInfoCustomizer> deploymentInfoCustomizers,
            ObjectProvider<UndertowBuilderCustomizer> builderCustomizers) {
        return new CthulhuServletWebServerFactory();
    }

    @Bean
    @SuppressWarnings("unused")
    CthulhuServletWebServerFactoryCustomizer cthulhuServletWebServerFactoryCustomizer(
            ServerProperties serverProperties) {
        return new CthulhuServletWebServerFactoryCustomizer();
    }

}
