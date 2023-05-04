package com.kul.controller;

import com.kul.domain.Order;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
public class ParamController {

    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable String name, @PathVariable Integer age) {
        System.out.println(name + ":" + age);
        return "参数是: " + name + "," + age;
    }

    @GetMapping("oneParam")
    public String oneParam(@RequestParam(required = true) String name){
        System.out.println(name);
        return "参数是: " + name;
    }

    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = true) String name, @RequestParam(required = true) Integer age){
        System.out.println(name + ":" + age);
        return "参数是: " + name + "," + age;
    }

    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order){
        System.out.println(order);
        return "参数是: " + order;
    }

    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order,@RequestParam(value = "name", required = false) String name){
        System.out.println(order + ":" + name);
        return "参数是: " + order + "," + name;
    }

    //单独传递时间对象有坑
    //发起端传入的时间和接收端接收到的时间并不一致
    //Fri May 05 00:43:53 CST 2023
    //Thu May 04 10:43:53 CST 2023
    //所以不建议单独传递时间参数,封装到对象里就不会出现上述问题;或者转出字符串进行传递
    @GetMapping("testTime")
    public String testTime(@RequestParam Date date){
        System.out.println(date);
        return "什么情况";
    }
}
