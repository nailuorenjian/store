package com.kuw.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

// User 类
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseEntity implements Serializable {

    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;

}
