package com.kul.controller;

import com.kul.client.CustomerFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerController {


    /**
     * 这里因为CustomerFeign有两个类都交给Bean管理,这里注入的话,需要借助Qualifier来指定具体是哪一个bean
     * 这是因为@Autowired默认依赖type注入,一旦type相同，就需要我们指定name
     * 这里可以直接用@Resource注解注入,@Resource默认是byName注入
     */
    @Qualifier("com.kul.client.CustomerFeign")
    @Autowired
    private CustomerFeign customerFeign;

    @GetMapping("customerRent")
    public String customerRent(){
        String result = customerFeign.rent();
        System.out.println(result);
        return result;
    }
}
