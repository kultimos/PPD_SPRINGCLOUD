package com.kul.feign;

import com.kul.domain.MyOrder;
import com.kul.feign.hystrix.MyUserOrderFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "my-order-service",fallback = MyUserOrderFeignHystrix.class)
public interface MyUserOrderFeign {

    @GetMapping("/order/getMyOrderByUserId")
    MyOrder getMyOrderByUserId(@RequestParam Integer userId);
}
