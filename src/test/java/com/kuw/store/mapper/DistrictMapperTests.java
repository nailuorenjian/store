package com.kuw.store.mapper;

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
public class DistrictMapperTests {
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent(){
        List<District> list = districtMapper.findByParent("210100");
        for (District d : list){
            System.out.println(d);
        }
    }


    @Test
    public void findByCode(){
        String name = districtMapper.findNameByCode("210123");
            System.err.println(name);

    }


}
