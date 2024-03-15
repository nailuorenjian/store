package com.kuw.store.service;

public interface ICartService {

    /**
     *  把商品添加到购物车
     * @param uid
     * @param pid 商品id
     * @param amount 新增数量
     * @param username 修改者
     */
    void addToCart(Integer uid, Integer pid, Integer amount, String username);

}
