package com.kul.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

//这个FeignClient必须指定value,这个value的内容是 服务提供方的应用名
@FeignClient(value = "order-service")
public interface UserOrderFeign {

    @GetMapping("doOrder")
    public String doOrder();

    @GetMapping("test")
    public String test();
}
