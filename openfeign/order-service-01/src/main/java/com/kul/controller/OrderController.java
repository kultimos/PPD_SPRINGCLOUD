package com.kul.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class OrderController {

    @GetMapping("doOrder")
    public String doOrder() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "豆浆油条";
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }

}
