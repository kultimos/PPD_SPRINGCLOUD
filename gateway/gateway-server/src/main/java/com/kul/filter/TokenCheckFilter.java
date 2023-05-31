package com.kul.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * token校验的过滤器
 */
@Component
public class TokenCheckFilter implements GlobalFilter, Ordered {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static final List<String> ALLOW_PATH = Arrays.asList("/login","/login-service/login","/teacher");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if(ALLOW_PATH.contains(exchange.getRequest().getURI().getPath())){
            return chain.filter(exchange);
        }
        //通常token都被放在请求头中,以Authorization为key,以Bearer+空格+token为value
        List<String> authorization = exchange.getRequest().getHeaders().get("Authorization");
        if(!CollectionUtils.isEmpty(authorization)) {
            String token = authorization.get(0);
            if(StringUtils.hasText(token)) {
//                String realToken = token.split("Bearer")[1].trim();
                String realToken = token.replaceFirst("Bearer", "");
                if(StringUtils.hasText(realToken) && stringRedisTemplate.hasKey(realToken)) {
                    //如果token存在,就放行
                    return chain.filter(exchange);
                }
            }
        }
        ServerHttpResponse response = exchange.getResponse();
        response.getHeaders().set("Content-Type", "text/html;charset=utf-8");
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("code", 401);
        map.put("mag","没token,哥们你未授权啊");
        ObjectMapper objectMapper = new ObjectMapper();
        byte[] bytes = new byte[0];
        try {
            bytes = objectMapper.writeValueAsBytes(map);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        DataBuffer wrap = response.bufferFactory().wrap(bytes);
        return response.writeWith(Mono.just(wrap));
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
