package com.kuw.store.controller;

import com.kuw.store.entity.Product;
import com.kuw.store.service.IProductService;
import com.kuw.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.kuw.store.controller.BaseController.OK;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductService productService;

    @RequestMapping("/details")
    public JsonResult findByParent(){
        List<Product> data = productService.findHotList();
        return new JsonResult(OK,data);
    }

    @RequestMapping("/{id}/good")
    public JsonResult findById(@PathVariable("id") Integer id) {
        Product data = productService.findById(id);
        return new JsonResult(OK,data);
    }
}
