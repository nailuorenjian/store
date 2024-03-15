package com.kuw.store.service.impl;

import com.kuw.store.entity.User;
import com.kuw.store.mapper.UserMapper;
import com.kuw.store.service.IUserService;
import com.kuw.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Integer insertUser(User user) {
        // 通过user参数来获取username
        String username = user.getUsername();

        // 判断username是否存在
        User result = userMapper.queryUserByUsername(username);

        // 如果记过不为空，则username 已经存在
        if (result != null){
            // 抛出异常
            throw new UserNameDuplicatedException("user name are used");
        }

        // 获取老密码
        String oldPassword = user.getPassword();
        // 密码md5加密 salt就是随机字符串
        // 设置盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        user.setSalt(salt);
        // 把密码和盐值作为一个整体进行加密处理
        String md5Password = getMD5Password(oldPassword, salt);
        // 设置新密码
        user.setPassword(md5Password);

        // 补全数据
        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        //执行注册功能 rows == 1
        Integer rows = userMapper.insertUser(user);
        if (rows != 1){
            throw new InsertException("insert user data failed");
        }
        return rows;
    }

    @Override
    public User login(String username, String password) {
        // 查询用户名
        User result = userMapper.queryUserByUsername(username);
        if (result == null){
            throw new UserNotFoundException("user data not found");
        }
        //匹配密码
        // 先查询数据库中加密后的密码
        String oldPassword = result.getPassword();

        // 和用户的输入密码进行比较
        // 先获取盐值
        String salt = result.getSalt();
        // 把用户的密码安装相同的md5加密
        String newMd5Password = getMD5Password(password,salt);

        // 比较密码
        if(!newMd5Password.equals(oldPassword)){
            throw new PasswordNotMatchException("password failed");
        }

        // 判断is_delete的字段是否为1
        if (result.getIsDelete() == 1){
            throw new UserNotFoundException("user not found");
        }

        // 不直接获取全部user内容，而是仅获取需要的数据，提升用户性能
        User user = new User();
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        // 返回用户头像
        user.setAvatar(result.getAvatar());

        // 返回用户数据，用于其他页面的数据展示
        return user;
    }

    @Override
    public void updateUserPassword(String username, String oldPassword, String newPassword) {
        //根据用户名找出目标用户
        User user = userMapper.queryUserByUsername(username);
        //取出当前用户的盐值
        String salt = user.getSalt();
        //将输入的旧密码和当前用户的盐值进行加密用来验证用户输入的旧密码是否正确
        String oldMd5Password = getMD5Password(oldPassword,salt);
        if (!oldMd5Password.equals(user.getPassword())) {
            throw new PasswordNotMatchException("密码不能相同");
        }
        //用旧密码的盐值来对新密码加密
        String newMd5Password = getMD5Password(newPassword, salt);

        Integer res = userMapper.updateUserPassword(username, newMd5Password, username, new Date());
        if (res != 1) {
            throw new InsertException("在修改密码的过程中产生了未知异常");
        }

    }

    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByid(uid);
        if(result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("user info not found");
        }

        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());

        return user;
    }

    @Override
    public void updateUserInfo(Integer uid, String username, User user) {
        // 查询用户是否存在
        User result = userMapper.findByid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("user info not found");
        }

        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());

        Integer rows = userMapper.updateUserInfo(user);
        if (rows != 1){
            throw new UpdateException("update failed");
        }

    }

    @Override
    public void updateAvatarByUid(Integer uid, String username, String avatar) {
        // 查询用户数据是否存在
        User result = userMapper.findByid(uid);
        if (result == null || result.getIsDelete() == 1){
            throw new UserNotFoundException("user info not found");
        }

        // 更新操作
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows != 1) {
            throw new InsertException("在上传头像的过程中产生了未知异常");
        }

    }


    // 定义一个md5加密处理
    private String getMD5Password(String password, String salt){
        // 加密3次
        for (int i = 0; i < 3; i++) {
            // md5加密算法的调用
            password = DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }

        // 返回加密后的密码
        return password;

    }

}
