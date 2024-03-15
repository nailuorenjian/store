package com.kuw.store.service;

import com.kuw.store.entity.Address;

import java.util.List;

// 收货地址接口
public interface IAddressService {
    void addNewAddress(Integer uid, String username, Address address);

    List<Address> getByUid(Integer uid);

    /**
     *  修改用户的地址为默认地址
     * @param aid 地址id
     * @param uid 用户id
     * @param username 执行修改的人
     */
    void setDefault(Integer aid, Integer uid, String username);

    void deleteByAid(Integer aid, Integer uid, String username);


}
