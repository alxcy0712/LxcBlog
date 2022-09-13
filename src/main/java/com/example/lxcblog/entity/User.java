package com.example.lxcblog.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private long uid;
    private String nickName;
    private String password;
    private String email;
    private String phoneNumber;
    private String code;

}
