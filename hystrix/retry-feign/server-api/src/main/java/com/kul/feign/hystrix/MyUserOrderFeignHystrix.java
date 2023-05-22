package com.kul.feign.hystrix;

import com.kul.domain.MyOrder;
import com.kul.feign.MyUserOrderFeign;
import org.springframework.stereotype.Component;

@Component
public class MyUserOrderFeignHystrix implements MyUserOrderFeign {
    @Override
    public MyOrder getMyOrderByUserId(Integer userId) {
        return MyOrder.builder().id("false").name("我错卡").price("0")
                .build();
    }
}
