package com.kuw.store.mapper;

import com.kuw.store.entity.Cart;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface CartMapper {

    Integer insert(Cart cart);

    /**
     *  更新购物车某件商品的数量
     * @param cid
     * @param num
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    Integer updateNumByCid(Integer cid,
                           Integer num,
                           String modifiedUser,
                           Date modifiedTime);

    Cart findByUidAndPid(Integer uid, Integer pid);
}
