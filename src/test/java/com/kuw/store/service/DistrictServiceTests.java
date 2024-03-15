package com.kuw.store.service;

import com.kuw.store.entity.Address;
import com.kuw.store.entity.District;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DistrictServiceTests {
    @Autowired
    private IDistrictService districtService;

    @Test
    public void findByParent(){

        List<District> list = districtService.findByParent("86");
        for (District d : list){
            System.out.println(d);
            System.err.println(d);
        }
    }

}
