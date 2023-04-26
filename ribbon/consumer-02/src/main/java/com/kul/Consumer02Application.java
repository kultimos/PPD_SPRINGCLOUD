package com.kul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Consumer02Application {

    public static void main(String[] args) {
        SpringApplication.run(Consumer02Application.class, args);
    }

    @Bean
    @LoadBalanced   //ribbon将开始管理这个bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
