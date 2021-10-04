package com.example.lxcblog.controller;


import com.example.lxcblog.entity.User;
import com.example.lxcblog.mapper.UserMapper;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;


    /**
     * 注册
     */
    @PostMapping("/register")
    public String addUser(@RequestBody User user){
        /**
         * 整体步骤：查询昵称是否违规->
         * 200 -- 成功
         * 400 -- 存在相同的昵称
         * 401 -- 存在相同的邮箱
         */
        int state = 0 ;
        if(userMapper.checkSameNickName(user.getNickname()) != null){
            state = 400;
        }else if(userMapper.checkSamePhone(user.getPhoneNumber()) != null){
            state = 401;
        }else if(userMapper.checkSameEmail(user.getEmail()) != null){
            state = 402;
        }else if(!"0".equals(userMapper.checkBannedText(user.getNickname()))){
            state = 403;
        }
        else{
            state = 200;
        }

        System.out.println(state);

        String res = null;
        switch(state){
            case 200:res = "添加成功";userMapper.addUser(user);break;
            case 400:res = "存在相同的昵称";break;
            case 401:res = "存在相同的电话号码";break;
            case 402:res = "存在相同的邮箱";break;
            case 403:res = "存在违禁词汇";break;
            default: res = "未知错误";break;
        }
        return res;
    };

    /**
     * 登录
     */
    static class TempUser{
        private String name;
        private String password;

        public String getName() {
            return name;
        }


        public String getPassword() {
            return password;
        }
    }

    @PostMapping("/login")
    public String login(HttpServletRequest request ,@RequestBody TempUser user1){
        String name = user1.getName();
        String password = user1.getPassword();
        if("".equals(user1.name) || "".equals(user1.password)){
            return "用户输入错误";
        }
        User user = userMapper.login(name,password);
        String res = "";


        if(user!=null){
            request.getSession().setAttribute("loginUser"+user.getUid(),user.getUid());
            redisTemplate.opsForValue().set("loginUser"+user.getUid(), user.getNickname());
            res = "登入成功";
        }else{
            res = "error";
        }
        return res;
    }

}
