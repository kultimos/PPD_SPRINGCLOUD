package com.kul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RentCarService01Application {

    public static void main(String[] args) {
        SpringApplication.run(RentCarService01Application.class, args);
    }

}
