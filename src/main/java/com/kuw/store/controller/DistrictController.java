package com.kuw.store.controller;

import com.kuw.store.entity.District;
import com.kuw.store.service.IDistrictService;
import com.kuw.store.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/district")
public class DistrictController extends BaseController{
    @Autowired
    private IDistrictService districtService;

    @RequestMapping({"/",""})
    public JsonResult<List<District>> findByParent(String parent){
        List<District> data = districtService.findByParent(parent);
        return new JsonResult<>(OK,data);
    }
}
