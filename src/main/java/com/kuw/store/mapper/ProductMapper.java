package com.kuw.store.mapper;

import com.kuw.store.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {

    List<Product> findHotList();

    Product findById(Integer id);
}
