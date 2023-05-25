package com.kul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/login")
    public String doLogin(String name,String pwd){
        System.out.println("name = " + name);
        System.out.println("pwd = " + pwd);
        return "success";
    }
}
