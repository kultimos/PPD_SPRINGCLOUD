package com.kul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public class IPCheckFilter implements GlobalFilter, Ordered {

    /**
     * 网关的并发比较高,所以不要在网关里操作mysql,性能较差;可以使用redis
     * 或者在内存中写好数据,然后从内存中获取
     **/
    public static final List<String> BLACK_LIST = Arrays.asList("localhost");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String ip = exchange.getRequest().getHeaders().getHost().getHostString();
        System.out.println("ip = " + ip);
        if (BLACK_LIST.contains(ip)) {
            ServerHttpResponse response = exchange.getResponse();
            response.getHeaders().set("Content-Type", "text/html;charset=utf-8");
            HashMap<String, Object> map = new HashMap<>(4);
            map.put("code", HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
            map.put("msg", "黑名单啊哥们");
            ObjectMapper objectMapper = new ObjectMapper();
            byte bytes[] = new byte[0];
            try {
                bytes = objectMapper.writeValueAsBytes(map);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            DataBuffer wrap = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(wrap));
            //如果在黑名单中,就不放行
        } else {
            return chain.filter(exchange);
        }
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
