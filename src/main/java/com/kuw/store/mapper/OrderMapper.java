package com.kuw.store.mapper;

import com.kuw.store.entity.Order;
import com.kuw.store.entity.OrderItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderMapper {
    /**
     *  插入订单数据
     * @param order
     * @return
     */
    Integer insertOrder(Order order);

    /**
     *  插入订单项的数据
     * @param orderItem
     * @return
     */
    Integer insertOrderItem(OrderItem orderItem);
}
