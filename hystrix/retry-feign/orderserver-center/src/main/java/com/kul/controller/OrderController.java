package com.kul.controller;

import com.kul.domain.MyOrder;
import com.kul.feign.MyUserOrderFeign;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController implements MyUserOrderFeign {

    @Override
    public MyOrder getMyOrderByUserId(Integer userId) {
        return MyOrder.builder()
                .name("青椒肉丝")
                .price("20")
                .id("no1")
                .build();
    }
}
