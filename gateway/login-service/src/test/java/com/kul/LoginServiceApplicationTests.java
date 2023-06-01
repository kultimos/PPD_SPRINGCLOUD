package com.kul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class LoginServiceApplicationTests {

    private Jedis jedis;

    @BeforeEach
    void contextLoads() {
        jedis = new Jedis("192.168.10.129",6379);
        jedis.auth("1234567a");
        jedis.select(0);
    }

    @Test
    void test() {
        String result = jedis.set("name","zhangsan");
        System.out.println(result);
    }

}
