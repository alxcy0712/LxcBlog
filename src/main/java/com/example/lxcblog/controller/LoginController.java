package com.example.lxcblog.controller;


import com.example.lxcblog.entity.Result;
import com.example.lxcblog.entity.User;
import com.example.lxcblog.entity.UserResult;
import com.example.lxcblog.mapper.UserMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 注册
     */
    @PostMapping("/register")
    public Result<?> addUser(@RequestBody User user) {
        /**
         * 整体步骤：查询昵称是否违规->
         * 200 -- 成功
         * 400 -- 存在相同的昵称
         * 401 -- 存在相同的邮箱
         */
        int state = 0;
        if (userMapper.checkSameNickName(user.getNickName()) != null) {
            state = 400;
        } else if (userMapper.checkSamePhone(user.getPhoneNumber()) != null) {
            state = 401;
        } else if (userMapper.checkSameEmail(user.getEmail()) != null) {
            state = 402;
        } else if (!"0".equals(userMapper.checkBannedText(user.getNickName()))) {
            state = 403;
        } else {
            state = 200;
        }

        System.out.println(state);


        switch (state) {
            case 200:
                userMapper.addUser(user);
                UserResult ur = new UserResult(user);
                return Result.success(ur);
            case 400:
                return Result.error("0", "存在相同的昵称", "存在相同的昵称");
            case 401:
                return Result.error("0", "存在相同的电话号码", "存在相同的电话号码");
            case 402:
                return Result.error("0", "存在相同的邮箱", "存在相同的邮箱");
            case 403:
                return Result.error("0", "存在违禁词汇", "存在违禁词汇");
            default:
                return Result.error("0", "未知错误", "未知错误");
        }
    };

    /**
     * 登录
     */
    static class TempUser {
        private String name;
        private String password;

        public String getName() {
            return name;
        }

        public String getPassword() {
            return password;
        }
    }

    /*
     * 0 失败
     * 非0 成功
     */
    @PostMapping("/login")
    public Result<?> login(@RequestBody TempUser user1) {

        String name = user1.getName();
        String password = user1.getPassword();
        User user = userMapper.login(name, password);
        int res = 0;
        if (user != null) {
            redisTemplate.opsForList().leftPush("loginUser" + user.getUid() , String.valueOf(user.getUid()));
            redisTemplate.opsForList().leftPush("loginUser" + user.getUid() , String.valueOf(user.getNickName()));
            res = 1;
        }
        if (res == 1) {
            UserResult ur = new UserResult(user);
            return Result.success(ur);
        } else {
            return Result.error("0", "失败", "用户名密码有误");
        }
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestBody User user) {

//        ============= 以下内容为1.0版本 =======================
        if (user != null) {
            redisTemplate.delete("loginUser" + user.getUid());
            return Result.success(user);
        } else {
            System.out.println("error");
            return Result.error("0", "失败", "未传入任何参数");
        }
        
    }

}
