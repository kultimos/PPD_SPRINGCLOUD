package com.kul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String doLogin(String name,String pwd){
        System.out.println("name = " + name);
        System.out.println("pwd = " + pwd);
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        System.out.println(zonedDateTime);
    }
}
