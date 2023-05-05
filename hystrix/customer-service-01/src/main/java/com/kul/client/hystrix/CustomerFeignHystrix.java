package com.kul.client.hystrix;

import com.kul.client.CustomerFeign;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import org.springframework.stereotype.Component;

/**
 * 熔断器需要由Bean进行管理
 * 这里熔断器和feign的关系很想service接口和serviceImpl实现类之间的关系
 * 目前我们也是通过熔断器实现Feign接口并重写其接口的方式来进行服务熔断处理
 * 同时也需要在Feign的注解中通过fallback配置熔断器的class文件
 */
@Component
public class CustomerFeignHystrix implements CustomerFeign {

    @Override
    public String rent() {
        return "我是服务故障时的备选方案";
    }
}
