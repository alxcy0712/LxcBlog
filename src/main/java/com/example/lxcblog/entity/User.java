package com.example.lxcblog.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private long uid;
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;
    private int exp;
}
