package com.kul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DiscoveryController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("doDiscovery")
    public String doDiscovery(String serviceName){

        //����Ƿ�����,ͨ�������Ӧ������,�ҵ�����ķ�����Ϣ
        List<ServiceInstance> instances= discoveryClient.getInstances(serviceName);
        instances.forEach(System.out::println);
        ServiceInstance serviceInstance = instances.get(0);
        String ip = serviceInstance.getHost();
        int port = serviceInstance.getPort();
        System.out.println("ip = " + ip);
        System.out.println("port = " + port);
        return serviceInstance.toString();
    }
}
