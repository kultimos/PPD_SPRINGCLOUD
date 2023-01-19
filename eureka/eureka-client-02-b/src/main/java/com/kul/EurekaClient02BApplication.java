package com.kul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EurekaClient02BApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaClient02BApplication.class, args);
    }

}
