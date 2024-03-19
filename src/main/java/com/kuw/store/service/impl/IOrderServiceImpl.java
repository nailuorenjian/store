package com.kuw.store.service.impl;

import com.kuw.store.entity.Address;
import com.kuw.store.entity.Order;
import com.kuw.store.entity.OrderItem;
import com.kuw.store.mapper.OrderMapper;
import com.kuw.store.service.IAddressService;
import com.kuw.store.service.ICartService;
import com.kuw.store.service.IOrderService;
import com.kuw.store.service.ex.InsertException;
import com.kuw.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class IOrderServiceImpl implements IOrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private ICartService cartService;

    @Override
    public Order creat(Integer aid, Integer uid, String username, Integer[] cids) {
        // 即将要下单的列表
        List<CartVO> list = cartService.getVoByCid(uid, cids);
        // 计算产品的总价
        Long totalPrice = 0L;

        for (CartVO c:list){
            // 循环遍历获取总价
            totalPrice += c.getRealPrice() * c.getNum();
        }

        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);

        // 收货地址
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceCode());
        order.setRecvCity(address.getCityCode());
        order.setRecvArea(address.getAreaCode());
        order.setRecvAddress(address.getAddress());

        // 支付，总价，时间
        order.setStatus(0);
        order.setTotalPrice(totalPrice);
        order.setOrderTime(new Date());

        // 日志
        order.setCreatedTime(new Date());
        order.setCreatedUser(username);
        order.setModifiedTime(new Date());
        order.setModifiedUser(username);

        // 查询数据
        Integer rows = orderMapper.insertOrder(order);
        if(rows != 1){
            throw new InsertException("order data insert failed");
        }

        // 创建订单项数据的数据
        for (CartVO c :list){
            // 创建一个订单项数据对象
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(c.getPid());
            orderItem.setTitle(c.getTitle());
            orderItem.setImage(c.getImage());
            orderItem.setPrice(c.getRealPrice());
            orderItem.setNum(c.getNum());

            // 日志
            orderItem.setCreatedTime(new Date());
            orderItem.setCreatedUser(username);
            orderItem.setModifiedTime(new Date());
            orderItem.setModifiedUser(username);

            // 插入数据
            rows = orderMapper.insertOrderItem(orderItem);
            if (rows != 1){
                throw new InsertException("insert orderItem data failed");
            }
        }

        return order;
    }
}
