package com.kul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigTestController {

    @Value("${hero.name}")
    private String name;

    @Value("${hero.team}")
    private String team;

    @Value("${hero.champions}")
    private String champions;

    @Value("${hero.mvp}")
    private String mvp;

    @GetMapping("/config")
    public String config() {
        return "name:" + name + ",team:" + team + ",champions:" + champions + ",mvp:" + mvp;
    }
}
