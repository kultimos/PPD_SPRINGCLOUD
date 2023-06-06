package com.kul.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("nacos-client-b")
@Component
public interface aFeign {

    @GetMapping("/info")
    public String info();
}
