package com.example.lxcblog.controller;


import com.alibaba.fastjson.JSON;
import com.example.lxcblog.entity.User;
import com.example.lxcblog.entity.UserResult;
import com.example.lxcblog.mapper.UserMapper;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")

//跨域解决办法
@CrossOrigin
public class UserController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/findAll")
    public List<User> findAll(){
        return userMapper.findAll();
    }

    @GetMapping("/getSessionId/id={id}")
    public String getSessionId(@PathVariable long id) throws NullPointerException {
        String name = redisTemplate.opsForValue().get("loginUser"+id);
        UserResult ur = new UserResult();
        ur.setUid(id);
        ur.setNickname(name);
        return JSON.toJSONString(ur);
    }
}
