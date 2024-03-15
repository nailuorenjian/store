package com.kuw.store.service.impl;

import com.kuw.store.entity.Cart;
import com.kuw.store.entity.Product;
import com.kuw.store.mapper.CartMapper;
import com.kuw.store.mapper.ProductMapper;
import com.kuw.store.service.ICartService;
import com.kuw.store.service.ex.InsertException;
import com.kuw.store.service.ex.UpdateException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ICartServiceImpl implements ICartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public void addToCart(Integer uid, Integer pid,
                          Integer amount, String username) {
        Date date = new Date();
        // 查询当前添加的这个购物是否已经在表中存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if(result == null){ // 表示这个商品没有被添加到购物车中
            // 创建cart对象
            Cart cart = new Cart();
            // 补全数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);

            //价格:来自于商品表中的数据
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());

            // 补全日志
            cart.setCreatedUser(username);
            cart.setCreatedTime(date);
            cart.setModifiedUser(username);
            cart.setModifiedTime(date);

            Integer rows = cartMapper.insert(cart);
            if(rows != 1){
                throw new InsertException("insert cart failed");
            }
        } else { // 已经存在，更新这条数据的num值
            Integer num = result.getNum() + amount;
            Integer rows = cartMapper.updateNumByCid(result.getCid(),
                    num,
                    username,
                    date);
            if(rows != 1){
                throw new UpdateException("update cart failed");
            }
        }

    }
}
