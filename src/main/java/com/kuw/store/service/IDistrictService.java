package com.kuw.store.service;

import com.kuw.store.entity.District;

import java.util.List;

public interface IDistrictService {

    /**
     *  根据父代号查询区域信息
     * @param parent
     * @return
     */
    List<District> findByParent(String parent);

    String getNameByCode(String code);

}
