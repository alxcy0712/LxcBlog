package com.example.lxcblog.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserResult extends User{
    private long uid;
    private String nickName;

    public UserResult (User user){
        this.uid = user.getUid();
        this.nickName = user.getNickName();
    }
}
