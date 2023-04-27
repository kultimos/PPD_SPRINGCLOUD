package com.kul.controller;


import com.kul.feign.UserOrderFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserOrderFeign userOrderFeign;

    /**
     * 需要注意的是 feign的默认等待时间是1s,超过1s就会报"超时"的错误
     * 所以如果远程调用耗时的接口,我们就要自己配置一下等待时间
     * @return
     */
    @GetMapping("userDoOrder")
    public String userDoOrder() {
        System.out.println("有用户进来了");
        String result = userOrderFeign.doOrder();
        return result;
    }

}
