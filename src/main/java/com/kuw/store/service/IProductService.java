package com.kuw.store.service;

import com.kuw.store.entity.Product;

import java.util.List;

public interface IProductService {

    List<Product> findHotList();

    Product findById(Integer id);
}
