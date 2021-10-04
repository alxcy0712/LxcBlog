package com.example.lxcblog.mapper;

import com.example.lxcblog.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userMapper")
public interface UserMapper {


    /**
     * 添加个人信息
     * @param user      用户所有信息
     */
    void addUser(User user);
    //添加个人信息的检查项目
    //查询是否有相同的nickname
    User checkSameNickName(String name);
    //查询是否有相同的email
    User checkSameEmail(String email);
    //查询是否有相同phone
    User checkSamePhone(String phone);
    //查询是否有违规词汇bannedText
    String checkBannedText(String name);


    /**
     * 登录
     */
    User login(String name , String password);

    /**
     * 查询所有用户的信息
     * @return
     */
    List<User> findAll();
}
