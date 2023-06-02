package com.kul.config;

import org.checkerframework.checker.units.qual.K;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import reactor.core.publisher.Mono;

/**
 * 自定义限流配置
 */
@Configuration
public class RequestLimitConfig {

    //针对某一个接口进行ip限流
    @Bean("ipKeyResolver")
    @Primary
    public KeyResolver ipKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getHeaders().getHost().getHostString()) ;
    }

    //针对路径来进行限流
    @Bean("apiKeyResolver")
    public KeyResolver apiKeyResolver(){
        return exchange -> Mono.just(exchange.getRequest().getPath().value());
    }

}
