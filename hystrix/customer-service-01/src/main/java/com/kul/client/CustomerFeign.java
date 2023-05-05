package com.kul.client;

import com.kul.client.hystrix.CustomerFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * fallback属性指定服务出现问题时,所选的熔断器的class文件
 */
@FeignClient(value = "rent-car-service",fallback = CustomerFeignHystrix.class)
public interface CustomerFeign {
    @GetMapping("rent")
    public String rent();
}
