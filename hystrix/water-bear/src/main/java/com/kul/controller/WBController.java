package com.kul.controller;

import com.kul.anno.MyHystrix;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class WBController {

    @Resource
    private RestTemplate restTemplate;


    @MyHystrix
    @GetMapping("doRpc")
    public String doRpc(){
        String result = restTemplate.getForObject("http://localhost:8354/abc", String.class);
        return result;
    }
}
