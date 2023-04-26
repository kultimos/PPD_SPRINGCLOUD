package com.kul;

import com.kul.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class RestTemplateApplicationTests {

    @Test
    void contextLoads() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://www.baidu.com";
        String result = restTemplate.getForObject(url, String.class);
        System.out.println(result);
    }

    @Test
    void testCon() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testGet?name=蔡徐坤";
        String re = restTemplate.getForObject(url,String.class);
        System.out.println(re);
    }

    @Test
    void testPost1() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testPost1";
        User user = new User("打呢么",13,56D);
        String result = restTemplate.postForObject(url,user,String.class);
        System.out.println(result);
    }

    @Test
    void testPost2() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/testPost2/";
        //采用表单参数的方式进行post请求的特殊写法
        LinkedMultiValueMap<String,Object> map = new LinkedMultiValueMap();
        map.add("name","姚明");
        map.add("age",26);
        map.add("price",8000D);
        String result = restTemplate.postForObject(url,map,String.class);
        System.out.println(result);
    }
}
