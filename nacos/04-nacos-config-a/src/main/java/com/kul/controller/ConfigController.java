package com.kul.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope //开启配置实时刷新,开启后,配置中心的配置修改后,不需要重启服务,就可以实时生效
public class ConfigController {

    /**
     * 从配置中心读取配置,并注入到属性中
     * 但是注意配置文件中不要出现中文,否则无法读取
     */

    @Value("${hero.name}")
    private String name;

    @Value("${hero.age}")
    private Integer age;

    @Value("${hero.address}")
    private String address;

    @GetMapping("/config")
    public String config() {
        return "name:" + name + ",age:" + age + ",address:" + address;
    }

}
