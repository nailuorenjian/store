package com.kuw.store.service.impl;

import com.kuw.store.entity.Product;
import com.kuw.store.mapper.ProductMapper;
import com.kuw.store.service.IProductService;
import com.kuw.store.service.ex.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    ProductMapper productMapper;

    @Override
    public List<Product> findHotList() {
        List<Product> products = productMapper.findHotList();

        //将不必要的数据设置为null，提升效率
        for (Product product : products) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }

        return products;
    }

    @Override
    public Product findById(Integer id) {
        Product p = productMapper.findById(id);
        if (p == null) {
            throw new ProductNotFoundException("商品信息没有找到");
        }

        //将不需要的数据设置为空，提升效率
        p.setPriority(null);
        p.setCreatedUser(null);
        p.setCreatedTime(null);
        p.setModifiedUser(null);
        p.setModifiedTime(null);
        return p;
    }

}
