package com.kuw.store.mapper;

import com.kuw.store.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DistrictMapper {
    /**
     *  根据符号查询区域信息
     * @param parent
     * @return 某个父区域下的所有区域列表
     */
    List<District> findByParent(String parent);

    String findNameByCode(String code);
}
