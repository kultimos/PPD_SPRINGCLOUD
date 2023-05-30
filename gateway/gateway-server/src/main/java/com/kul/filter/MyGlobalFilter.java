package com.kul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Objects;

@Component
public class MyGlobalFilter implements GlobalFilter, Ordered {

    /**
     * 这个就是过滤的方法
     * @param exchange  这个是请求和响应的上下文,可以获取到请求和响应的所有内容
     * @param chain    这个是过滤器链,可以通过这个链条来决定是否放行
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        System.out.println("路径path = " + request.getPath());
        System.out.println("请求方式method = " + request.getMethod());
        System.out.println("请求参数param = " + request.getQueryParams());
        System.out.println("请求头header = " + request.getHeaders());
        System.out.println("请求体body = " + request.getBody());
        System.out.println("主机名称 = " + request.getRemoteAddress().getHostName());

        System.out.println("--------------------------------------------");
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type","text/html;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", HttpStatus.UNAUTHORIZED.value());
        map.put("msg","非法请求");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer wrap = response.bufferFactory().wrap(bytes); //通过包装工厂将字节数组包装成一个数据包
//        return response.writeWith(Mono.just(wrap));
        return chain.filter(exchange); //放行,如果不放行,就不会继续执行后面的过滤器;过滤器可能会有多个,所以需要过滤器链
    }

    /**
     * 这个方法是用来设置过滤器的优先级的,数字越小,优先级越高
     * @return
     */
    @Override
    public int getOrder() {
        return 4;
    }
}
