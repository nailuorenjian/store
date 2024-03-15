package com.kuw.store.controller;

import com.kuw.store.controller.ex.*;
import com.kuw.store.service.ex.*;
import com.kuw.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class BaseController{

    // 成功的状态码
    public static final int OK = 200;

    @ExceptionHandler({ServiceException.class,FileUploadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UserNameDuplicatedException){
            result.setState(4000);
            result.setMessage("user name are used");
        } else if (e instanceof UserNotFoundException) {
            result.setState(4001);
            result.setMessage("username not found");
        } else if (e instanceof PasswordNotMatchException) {
            result.setState(4002);
            result.setMessage("password not match");
        } else if (e instanceof AddressCountLimitException) {
            result.setState(4003);
            result.setMessage("user address count over limit");
        } else if (e instanceof AddressNotFoundException) {
            result.setState(4004);
            result.setMessage("address not found");
        } else if (e instanceof AccessDeniedException) {
            result.setState(4005);
            result.setMessage("user address disallowed visit");
        }else if (e instanceof ProductNotFoundException) {
            result.setState(4006);
            result.setMessage("product not found");
        }else if (e instanceof InsertException) {
            result.setState(5000);
            result.setMessage("user insert failed");
        } else if (e instanceof UpdateException) {
            result.setState(5001);
            result.setMessage("user update failed");
        } else if (e instanceof FileEmptyException) {
            result.setState(6000);
            result.setMessage("file update failed");
        } else if (e instanceof FileSizeException) {
            result.setState(6001);
        } else if (e instanceof FileTypeException) {
            result.setState(6002);
        } else if (e instanceof FileStateException) {
            result.setState(6003);
        } else if (e instanceof FileUploadIOException) {
            result.setState(6004);
        }
        return result;
    }

    // 获取session对象的uid
    // return 登录用户uid的值
    public final Integer getUidFromSession(HttpSession session){
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    public final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }


}
