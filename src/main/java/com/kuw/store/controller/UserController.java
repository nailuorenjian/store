package com.kuw.store.controller;

import com.kuw.store.controller.ex.*;
import com.kuw.store.entity.User;
import com.kuw.store.service.IUserService;
import com.kuw.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/insertuser")
    public JsonResult<Void> insertUser(User user){
        userService.insertUser(user);
        return new JsonResult<>(OK);
    }

//    @RequestMapping("reg")
//    public JsonResult<Void> reg(User user){
//        JsonResult<Void> result = new JsonResult<>();
//        try{
//            userService.insertUser(user);
//            result.setState(200);
//            result.setMessage("user name are insert");
//        }catch (UserNameDuplicatedException e){
//            result.setState(4000);
//            result.setMessage("user name are used");
//        }catch (InsertException e){
//            result.setState(5000);
//            result.setMessage("user insert failed");
//        }
//        return result;
//    }

    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session){
        User data = userService.login(username,password);

        // 把uid 和username 赋值给session
        session.setAttribute("uid",data.getUid());
        session.setAttribute("username",data.getUsername());

//        System.out.println(getUidFromSession(session));
//        System.out.println(getUsernameFromSession(session));

        return new JsonResult<User>(OK,data);
    }


    @RequestMapping("/update")
    public JsonResult<User> updateUserPassword(String username, String oldPassword,
                                               String newPassword, HttpSession session){

        String userName = getUsernameFromSession(session);
        userService.updateUserPassword(userName,oldPassword,newPassword);
        return new JsonResult<User>(OK);
    }

    @RequestMapping("/getbyuid")
    public JsonResult<User> getByUid(HttpSession session){
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/updateuserinfo")
    public JsonResult<User> updateuserinfo(User user, HttpSession session){
        // 获取 uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        userService.updateUserInfo(uid,username,user);
        return new JsonResult<User>(OK);
    }

    // 设置文件上传的最大值
    public static final int AVATAR_MAX_SIZEE = 10 * 1024 * 1024;

    // 限制上传文件的类型
    public static final List<String> AVATAR_TYPE = new ArrayList<>();
    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/gif");
        AVATAR_TYPE.add("image/bmp");
    }

    // @RequestParam("file") 为了应对前后端的参数名不一致的问题
    @RequestMapping("/updateAvatarByUid")
    public JsonResult<String> updateAvatarByUid(HttpSession session,
                                                @RequestParam("file") MultipartFile file){
        // 判断文件是否为空
        if(file.isEmpty()){
            throw new FileEmptyException("file is empty");
        }

        // 判断大小
        if (file.getSize() > AVATAR_MAX_SIZEE){
            throw new FileSizeException("file is over size");
        }

        // 判断类型
        String contentType = file.getContentType();
        // 判断上传文件AVATAR_TYPE是否包含 contentType
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("file type wrong");
        }

        // 上传的文件 /upload/img.png
        String parent = session.getServletContext().getRealPath("upload");

        // file对象指向这个路径，File是否存在
        File dir = new File(parent);
        if (!dir.exists()){ //检测目录是否存在
            dir.mkdirs(); //创建当前的目录
        }

        // 获取文件名，并用UUID重命名
        String originalFilename = file.getOriginalFilename();
        System.out.println(originalFilename);
        int index = originalFilename.lastIndexOf(".");
        //获取后缀名
        String suffix = originalFilename.substring(index);
        // UUID+后缀名
        String filename = UUID.randomUUID().toString().toUpperCase() + suffix;

        // 创建一个空文件
        File dest = new File(dir, filename);
        // 参数file中的数据写入空文件中
        try {
            // 把file文件的数据 写入到dest文件中
            file.transferTo(dest);
        } catch (IOException e) {
           throw new FileUploadException("file transferTo error");
        } catch (FileStateException e){
            throw new FileUploadException("file state error");
        }

        // 获取uid等参数
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        // 返回头像的路径/upload/test.png
        String avatar = "/upload/" + filename;
        userService.updateAvatarByUid(uid,username,avatar);
        // 返回用户头像的路径给前端
        return new JsonResult<>(OK,avatar);

    }
}
