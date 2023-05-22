package com.kul.controller;

import com.kul.domain.MyOrder;
import com.kul.feign.MyUserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyUserController {

    @Qualifier("com.kul.feign.MyUserOrderFeign")
    @Autowired
    private MyUserOrderFeign myUserOrderFeign;

    @GetMapping("/findOrder")
    public MyOrder findOrder(){
        return myUserOrderFeign.getMyOrderByUserId(1);
    }
}
