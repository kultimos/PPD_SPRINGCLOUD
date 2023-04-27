package com.kul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //开启feign客户端功能,才可以发起调用
public class UserService01Application {

    public static void main(String[] args) {
        SpringApplication.run(UserService01Application.class, args);
    }

}
