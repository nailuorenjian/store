package com.kuw.store.service;

import com.kuw.store.vo.CartVO;

import java.util.List;

public interface ICartService {

    /**
     *  把商品添加到购物车
     * @param uid
     * @param pid 商品id
     * @param amount 新增数量
     * @param username 修改者
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

    List<CartVO> getVoByUid(Integer uid);

    /**
     *  更新用户购物车数据的数量
     * @param cid
     * @param uid
     * @param username
     * @return 增加成功后新的数量
     */
    Integer addNum(Integer cid, Integer uid, String username);

    Integer reduceNum(Integer cid, Integer uid, String username);

    List<CartVO> getVoByCid(Integer cid, Integer[] cids);



}
