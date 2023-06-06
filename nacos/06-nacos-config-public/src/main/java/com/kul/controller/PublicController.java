package com.kul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class PublicController {

    @Value("${hero.name}")
    private String name;

    @Value("${hero.team}")
    private String team;

    @Value("${hero.host}")
    private String host;


    @GetMapping("/public")
    public String publicInfo() {
        return "name: " + name + ", team: " + team + ", host: " + host;
    }
}
