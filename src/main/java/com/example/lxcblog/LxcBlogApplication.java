package com.example.lxcblog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuxiaochen
 */
@SpringBootApplication
@MapperScan(value = "com.example.lxcblog.mapper")
public class LxcBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LxcBlogApplication.class, args);
    }

}
