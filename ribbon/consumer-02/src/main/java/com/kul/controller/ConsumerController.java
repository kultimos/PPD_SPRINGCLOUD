package com.kul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate template;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("testRibbon")
    public String testRibbon(String serviceName){
        // 这里最终的url实际上是http://provider/hello
        // 这里如果没有ribbon是根本不好使的
        // 但是由ribbon代理后的restTemplate则具有了可以根据服务名在注册列表中找到服务并请求的能力
        // 也可以理解为通过服务名拿到对应服务的ip和端口
        // ribbon
        // 1.拦截初始请求 2.截取主机名称 3.借助eureka来做服务发现
        // 4.通过负载均衡拿到服务的ip和port 5.reConstructURL 重构请求url
        // 6.发送请求
        String result = template.getForObject("http://" + serviceName + "/hello", String.class);
        return result;
    }


    @GetMapping("testRibbonRule")
    public String testRibbonRule(String serviceName) {
        ServiceInstance choose = loadBalancerClient.choose(serviceName);
        return choose.toString();
    }
}
