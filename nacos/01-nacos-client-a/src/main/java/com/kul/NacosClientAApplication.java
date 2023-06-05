package com.kul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient // 开启服务发现客户端
public class NacosClientAApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosClientAApplication.class, args);
    }

}
