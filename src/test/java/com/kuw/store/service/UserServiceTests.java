package com.kuw.store.service;

import com.kuw.store.entity.User;
import com.kuw.store.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTests {
    @Autowired
    private IUserService userService;

    @Test
    public void insertUser(){
        User user = new User();
        user.setUsername("test9");
        user.setPassword("123");
        Integer rows = userService.insertUser(user);
        System.out.println(rows);
    }

    @Test
    public void login(){
        User user = userService.login("test9","123");
        System.out.println(user);
    }


    @Test
    public void upAvatarByUid(){
        userService.updateAvatarByUid(28,
                "test",
                "/Users/user/Downloads/test/aa/hello.jpg");
    }

}
