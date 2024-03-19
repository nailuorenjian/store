package com.kuw.store.service;

import com.kuw.store.entity.Address;
import com.kuw.store.entity.Order;

public interface IOrderService {
    Order creat(Integer aid, Integer uid, String username, Integer cids[]);
}
