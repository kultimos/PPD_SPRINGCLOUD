package com.kul.feign;

import com.kul.domain.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

//这个FeignClient必须指定value,这个value的内容是 服务提供方的应用名
@FeignClient(value = "order-service")
public interface UserOrderFeign {

    @GetMapping("doOrder")
    public String doOrder();

    @GetMapping("testUrl/{name}/and/{age}")
    public String testUrl(@PathVariable String name, @PathVariable Integer age);

    @GetMapping("oneParam")
    public String oneParam(@RequestParam(required = true) String name);

    @GetMapping("twoParam")
    public String twoParam(@RequestParam(required = true) String name, @RequestParam(required = true) Integer age);

    @PostMapping("oneObj")
    public String oneObj(@RequestBody Order order);

    @PostMapping("oneObjOneParam")
    public String oneObjOneParam(@RequestBody Order order,@RequestParam(value = "name", required = false) String name);

    //单独传递时间对象
    @GetMapping("testTime")
    public String testTime(@RequestParam Date date);
}
