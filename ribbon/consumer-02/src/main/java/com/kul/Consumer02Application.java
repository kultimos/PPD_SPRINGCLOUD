package com.kul;

import com.kul.controller.config.MyRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.config.ExecutorBeanDefinitionParser;
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

    //除了可以在yml中进行负载均衡算法的配置,还可以在bean中配置,不过在bean中配置是全局配置
    @Bean
    public IRule myRule() {
        return new RandomRule();
//        return new MyRule();  引入自定义的负载均衡策略配置
    }
}
