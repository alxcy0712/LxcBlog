package com.example.lxcblog.controller;


import com.example.lxcblog.entity.User;
import com.example.lxcblog.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/user")

/*
  跨域解决办法
 */
@CrossOrigin
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/findAll")
    public List<User> findAll(){
        return userMapper.findAll();
    }

}
