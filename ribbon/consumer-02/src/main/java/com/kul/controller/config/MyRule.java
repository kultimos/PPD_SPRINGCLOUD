package com.kul.controller.config;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import org.springframework.stereotype.Component;

//可以实现IRule方法实现自定义负载均衡策略
@Component
public class MyRule implements IRule {
    @Override
    public Server choose(Object o) {
        return null;
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {

    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return null;
    }
}
