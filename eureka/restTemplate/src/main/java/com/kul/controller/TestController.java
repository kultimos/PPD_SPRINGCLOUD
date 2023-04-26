package com.kul.controller;

import com.kul.domain.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("testGet")
    public String get(String name){
        System.out.println(name);
        return "ok";
    }

    @PostMapping("testPost1")
    public String testPost1(@RequestBody User user){
        System.out.println(user);
        return "ok";
    }


    /**
     * 该请求的接收参数是一个表单对象
     * 即header content-type = x-www-form-undercode
     * 目前看区别就是不需要@RequestBody了
     * @param user
     * @return
     */
    @PostMapping("testPost2")
    public String testPost2(User user) {
        System.out.println(user);
        return "这里是表单参数的请求";
    }
}
