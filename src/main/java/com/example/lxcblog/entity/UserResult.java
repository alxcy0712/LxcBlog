package com.example.lxcblog.entity;

import lombok.Data;

@Data
public class UserResult extends User{
    private long uid;
    private String nickName;
    private int exp;
}
