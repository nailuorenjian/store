package com.kuw.store.service.impl;

import com.kuw.store.entity.District;
import com.kuw.store.mapper.DistrictMapper;
import com.kuw.store.service.IDistrictService;
import com.kuw.store.service.ex.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements IDistrictService {

    @Autowired
    private DistrictMapper districtMapper;

    @Override
    public List<District> findByParent(String parent) {
        List<District> list = districtMapper.findByParent(parent);
        // 传输数据时，避免无效数据，将其设置为bull
        for (District d : list){
            d.setId(null);
            d.setParent(null);
        }
        return list;
    }

    @Override
    public String getNameByCode(String code) {
        return districtMapper.findNameByCode(code);
    }

}
