package com.kuw.store.service.impl;

import com.kuw.store.entity.Cart;
import com.kuw.store.entity.Product;
import com.kuw.store.mapper.CartMapper;
import com.kuw.store.mapper.ProductMapper;
import com.kuw.store.service.ICartService;
import com.kuw.store.service.ex.AccessDeniedException;
import com.kuw.store.service.ex.CartNotFoundException;
import com.kuw.store.service.ex.InsertException;
import com.kuw.store.service.ex.UpdateException;
import com.kuw.store.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.channels.AcceptPendingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
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

    @Override
    public List<CartVO> getVoByUid(Integer uid) {
        List<CartVO> voList = cartMapper.findVoByUid(uid);
        return voList;
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null){
            throw new CartNotFoundException("cart data not found");
        }

        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("not allowed visit data");
        }

        Integer num = result.getNum() + 1;
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1){
            throw new UpdateException("cart num update failed");
        }
        return num;
    }

    @Override
    public Integer reduceNum(Integer cid, Integer uid, String username) {
        Cart result = cartMapper.findByCid(cid);
        if (result == null){
            throw new CartNotFoundException("cart data not found");
        }

        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("not allowed visit data");
        }

        Integer num = result.getNum();

        if(result.getNum() > 1){
            num = result.getNum() - 1;
        }
        Integer rows = cartMapper.updateNumByCid(cid, num, username, new Date());
        if (rows != 1){
            throw new UpdateException("cart num update failed");
        }
        return num;    }

    @Override
    public List<CartVO> getVoByCid(Integer uid, Integer[] cids) {
        List<CartVO> list = cartMapper.findVoByCid(cids);
        // 迭代list
        Iterator<CartVO> it = list.iterator();
        while ((it.hasNext())){
            //取出迭代器中的每个list
            CartVO cartVO = it.next();
            if (!cartVO.getUid().equals(uid)) {
                //如果数据归属错误，就删除这个数据
                list.remove(cartVO);
            }
        }
        return list;
    }

}
