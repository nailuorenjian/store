package com.kuw.store.mapper;

import com.kuw.store.entity.Address;
import com.kuw.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressMapperTests {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void insert(){
        Address address = new Address();
        address.setUid(28);
        address.setPhone("12345678901");
        address.setName("testUser");
        addressMapper.insert(address);
    }

    @Test
    public void countByUid(){
        Integer count = addressMapper.countByUid(27);
        System.out.println(count);
    }

    @Test
    public void findByUid(){
        List<Address> list = addressMapper.findByUid(28);
        for (Address a: list){
            System.out.println(a);
        }
    }

    @Test
    public void findByAid(){
        System.out.println(addressMapper.findByAid(29));
    }


}
