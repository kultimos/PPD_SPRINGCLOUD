package com.kul;

import com.kul.controller.UserController;
import com.kul.feign.UserOrderFeign;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@SpringBootTest
class UserService01ApplicationTests {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() {
        UserOrderFeign o = (UserOrderFeign) Proxy.newProxyInstance(UserController.class.getClassLoader(), new Class[]{UserOrderFeign.class}, new InvocationHandler() {
            /* 在具体理解这段代码之前,需要先清除代码的执行顺序,是先执行String s = o.doOrder();
            *  然后把o的代码对象和 doOrder这个方法名以及相关方法参数传入了invoke方法
            *  如果我们这里调用的是o.test()方法,那么传入的方法名和参数列表就是test()相关的内容了
            *  这是动态代理实现的
            * */
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                //获取GetMapping修饰的注解相关信息
                GetMapping annotation = method.getAnnotation(GetMapping.class);
                //拿到该注解的修饰的方法名
                String paths[] = annotation.value();
                String path = paths[0];
                //拿到传入的method方法的声明类
                Class<?> aClass = method.getDeclaringClass();
                //获取该声明类配置的拥有@feignClient注解的接口
                FeignClient annotation1 = aClass.getAnnotation(FeignClient.class);
                //获得@feignClient注解上配置的value,这个value就是我们将要远程调用的服务的服务名
                String applicationName = annotation1.value();
                //拼接请求,通过ribbon的管理,先去Eureka上找符合该应用名的服务,然后通过restTemplate调用该服务拿到返回值
                String url = "http://" + applicationName + "/" + path;
                String forObject = restTemplate.getForObject(url,String.class);
                return forObject;
            }
        });
        String s = o.doOrder();
        System.out.println(s);
    }

}
