package com.kuw.store.controller;

import com.kuw.store.service.ICartService;
import com.kuw.store.util.JsonResult;
import com.kuw.store.vo.CartVO;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shopcar")
public class CartController extends BaseController{

    @Autowired
    ICartService cartService;

    @RequestMapping("/addtoshopcar")
    public JsonResult addToShopCar(Integer pid, Integer amount, HttpSession session) {

        cartService.addToCart(getUidFromSession(session), pid, amount, getUsernameFromSession(session));
        return new JsonResult(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<CartVO>> getVoByUid(HttpSession session) {
        List<CartVO> data = cartService.getVoByUid(getUidFromSession(session));
        return new JsonResult(OK,data);
    }

    @RequestMapping("{cid}/addnum")
    public JsonResult addNum(@PathVariable("cid") Integer cid, HttpSession session) {

        Integer data = cartService.addNum(
                cid,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult(OK,data);
    }

    @RequestMapping("{cid}/reducenum")
    public JsonResult reduceNum(@PathVariable("cid") Integer cid, HttpSession session) {

        Integer data = cartService.reduceNum(
                cid,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult(OK,data);
    }

    @RequestMapping("/list")
    public JsonResult getVoByCid(Integer cids[], HttpSession session) {

        List<CartVO> data = cartService.getVoByCid(getUidFromSession(session), cids);

        return new JsonResult(OK,data);
    }

}
