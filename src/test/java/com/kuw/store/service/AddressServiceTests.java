package com.kuw.store.service;

import com.kuw.store.entity.Address;
import com.kuw.store.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class AddressServiceTests {
    @Autowired
    private IAddressService addressService;

    @Test
    public void addNewAddress(){
        Address address = new Address();
        address.setPhone("12345678901");
        address.setName("testUser");
        addressService.addNewAddress(27,"testAdd",address);

    }

    @Test
    public void setDefault(){
        addressService.setDefault(29,28,"testA");
    }

}
