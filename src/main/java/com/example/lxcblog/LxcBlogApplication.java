package com.example.lxcblog;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author liuxiaochen
 */
@SpringBootApplication
@EnableRedisHttpSession
@MapperScan(value = "com.example.lxcblog.mapper")
public class LxcBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxcBlogApplication.class, args);
    }

}
