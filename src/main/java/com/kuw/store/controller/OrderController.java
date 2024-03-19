package com.kuw.store.controller;

import com.kuw.store.entity.Order;
import com.kuw.store.service.IOrderService;
import com.kuw.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController{

    @Autowired
    private IOrderService orderService;

    @RequestMapping("/createorder")
    public JsonResult<Order> create(Integer aid, Integer[] cids,
                                    HttpSession session){

        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);

        Order order = orderService.creat(aid, uid, username, cids);

        return new JsonResult<>(OK, order);

    }
}
