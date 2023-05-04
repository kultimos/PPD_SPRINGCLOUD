package com.kul.controller;


import com.kul.domain.Order;
import com.kul.feign.UserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


@RestController
public class UserController {

    @Autowired
    private UserOrderFeign userOrderFeign;

    /**
     * 需要注意的是 feign的默认等待时间是1s,超过1s就会报"超时"的错误
     * 所以如果远程调用耗时的接口,我们就要自己配置一下等待时间
     * @return
     */
    @GetMapping("userDoOrder")
    public String userDoOrder() {
        System.out.println("有用户进来了");
        String result = userOrderFeign.doOrder();
        return result;
    }

    @GetMapping("testParam/name={name}&age={age}")
    public String testParam(@PathVariable String name, @PathVariable Integer age){
        String result1 = userOrderFeign.testUrl(name, age);
        String result2 = userOrderFeign.twoParam(name, age);
        String result3 = userOrderFeign.oneParam(name);
        //构建者模式
        Order order = Order.builder()
                .name(name)
                .id(age)
                .build();

        order.setName(name);
        order.setId(age);
        String result4 = userOrderFeign.oneObj(order);
        String result5 = userOrderFeign.oneObjOneParam(order,name);
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
        System.out.println(result4);
        System.out.println(result5);
        Date date = new Date();
        System.out.println(date);
        String result6 = userOrderFeign.testTime(date);
        System.out.println(result6);
        return "";
    }



}
