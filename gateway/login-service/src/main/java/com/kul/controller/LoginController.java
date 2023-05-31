package com.kul.controller;

import com.kul.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
public class LoginController {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @GetMapping("/login")
    public String doLogin(String name,String pwd){
        System.out.println("name = " + name);
        System.out.println("pwd = " + pwd);
        User user = User.builder().age(1).name("1").pwd("1").id(1).build();
        String token = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(token,user.toString(),Duration.ofSeconds(7200));
        int a = 10;
        String w = stringRedisTemplate.opsForValue().get(token);
        System.out.println(w);
        return token;
    }

    @GetMapping("/teacher")
    public String teacher(String name,String pwd){
        System.out.println("name = " + name);
        System.out.println("pwd = " + pwd);
        return "老师好";
    }


    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
    }
}
