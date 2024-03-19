package com.kuw.store.service.impl;

import com.kuw.store.entity.Address;
import com.kuw.store.mapper.AddressMapper;
import com.kuw.store.service.IAddressService;
import com.kuw.store.service.IDistrictService;
import com.kuw.store.service.ex.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

// 收货地址的实现类
@Service
public class AddressServiceImpl implements IAddressService {

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private IDistrictService districtService;

    @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
        // 调用收货地址 统计的方法
        Integer count = addressMapper.countByUid(uid);
        if (count >= maxCount){
            throw new AddressCountLimitException("too many address over limit");
        }

        // 对address的数据补全，省市区
        String provinceName = districtService.getNameByCode(address.getProvinceCode());
        String cityName = districtService.getNameByCode(address.getCityCode());
        String areaName = districtService.getNameByCode(address.getAreaCode());

        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);

        // uid, isDefault
        address.setUid(uid);
        // count 为0时 设置为1 默认地址，否则设置为0 非默认地址
        Integer isDefault = count == 0 ? 1 : 0;
        address.setIsDefault(isDefault);

        // 补全4项日志
        address.setCreatedUser(username);
        address.setModifiedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());

        // 插入地址的方法
        Integer rows = addressMapper.insert(address);
        if (rows != 1){
            throw new InsertException("insert address error");
        }

    }

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address : list){
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return list;
    }

    @Override
    public void setDefault(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null){
            throw new AddressNotFoundException("address not found");
        }

        // 检测收货地址的归属
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("not disallowed data visit");
        }

        // 设置所有地址为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows < 1){
            throw new UpdateException("address data update error");
        }

        // 修改用户的地址为默认地址 aid 地址id username 修改人
        rows = addressMapper.updateDefaultByAid(aid,username,new Date());
        if (rows != 1){
            throw new UpdateException("address default data update error");
        }

    }

    @Override
    public void deleteByAid(Integer aid, Integer uid, String username) {
        Address result = addressMapper.findByAid(aid);
        if (result == null){
            throw new AddressNotFoundException("address not found");
        }

        // 检测收货地址的归属
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("not disallowed data visit");
        }

        Integer rows = addressMapper.deleteByAid(aid);
        if (rows != 1){
            throw new DeleteException("delete address data failed");
        }

        if (result.getIsDefault() == 0) {
            return;
        }

        Integer count = addressMapper.countByUid(uid);
        if (count == 0){
            //终止程序
            return;
        }

        Address address = addressMapper.findLastModified(uid);
        String modifiedUser = address.getModifiedUser();

        Integer integer = addressMapper.updateDefaultByAid(aid, modifiedUser, new Date());
        if (integer != 1){
            throw new UpdateException("delete address set address isDefault failed");
        }


    }

    @Override
    public Address getByAid(Integer aid, Integer uid) {
        Address address = addressMapper.findByAid(aid);
        if (address == null){
            throw new AddressNotFoundException("address not found");
        }
        if(!address.getUid().equals(uid)){
            throw new AccessDeniedException("not allowed visit");
        }
        address.setProvinceCode(null);
        address.setCityName(null);
        address.setAreaName(null);
        address.setCreatedTime(null);
        address.setCreatedUser(null);
        address.setModifiedTime(null);
        address.setModifiedUser(null);
        return address;
    }


}
