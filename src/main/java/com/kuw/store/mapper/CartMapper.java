package com.kuw.store.mapper;

import com.kuw.store.entity.Cart;
import com.kuw.store.vo.CartVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

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

    List<CartVO> findVoByUid(Integer uid);

    Cart findByCid(Integer cid);

    List<CartVO> findVoByCid(Integer[] cids);

}
