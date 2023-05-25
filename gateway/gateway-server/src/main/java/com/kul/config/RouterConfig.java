package com.kul.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouterConfig {
    /**
     * 代码路由如下 ; 代码路由和配置路由可以共存，不过代码路由的优先级高于配置文件，如果配置文件中有相同的路由，那么代码路由会覆盖配置文件中的路由
     * @param builder
     * @return
     */

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("guochuang-id", r -> r.path("/guochuang")
                        .uri("http://www.bilibili.com"))
                .route("dance-id", r -> r.path("/v/dance")
                        .uri("http://www.bilibili.com"))
                .route("kichiku-id", r -> r.path("/v/kichiku")
                        .uri("http://www.bilibili.com"))
                .build();
    }
}
