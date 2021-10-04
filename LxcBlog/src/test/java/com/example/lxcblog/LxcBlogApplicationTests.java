package com.example.lxcblog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootTest
class LxcBlogApplicationTests {

    @Autowired
    StringRedisTemplate stringRedisTemplate ;

    @Test
    public void test1 (){
        stringRedisTemplate.opsForValue().append("msg","coder");
        stringRedisTemplate.opsForList().leftPush("mylist","1");
        stringRedisTemplate.opsForList().leftPush("mylist","2");
    }

    @Test
    void contextLoads() {
    }

}
