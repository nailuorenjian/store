package com.kuw.store.mapper;

import com.kuw.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void insertUser(){
        User user = new User();
        user.setUsername("test3");
        user.setPassword("123");
        Integer rows = userMapper.insertUser(user);
        System.out.println(rows);
    }

    @Test
    public void findByUserName(){
        User byid = userMapper.queryUserByUsername("kuw");
        System.out.println(byid);
    }

    @Test
    public void findByUid(){
        User byid = userMapper.findByid(27);
        System.out.println(byid);
    }


    @Test
    public void updataPwd(){
        userMapper.updateUserPassword("test9","321","test", new Date());
    }

    @Test
    public void updateAvatarByUid(){
        Integer byUid = userMapper.updateAvatarByUid(27,
                "/Users/user/Downloads/test/aa/hello.jpg",
                "test",
                new Date());
    }

}
