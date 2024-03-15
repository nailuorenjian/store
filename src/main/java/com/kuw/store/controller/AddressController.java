package com.kuw.store.controller;

import com.kuw.store.entity.Address;
import com.kuw.store.service.IAddressService;
import com.kuw.store.util.JsonResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController extends BaseController{

    @Autowired
    private IAddressService addressService;

    @RequestMapping("/addnewaddress")
    public JsonResult<Void> addNewAddress(Address address, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid, username, address);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"","/"})
    public JsonResult<List<Address>> getByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return new JsonResult<>(OK,data);
    }

    /**
     * restful风格，可以将请求路径中的{}中的值赋给方法的同名参数，@PathVariable注解是将请求路径中的某个值赋给它跟着的参数，
     * 这里意思是将路径中的aid赋值给参数中的aid,在前端发起请求时携带的参数要和你这里的{}所在的位置一致
     * @param aid
     * @param session
     * @return
     */
    @RequestMapping("/{aid}/setdefault")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session){
        addressService.setDefault(
                aid,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult<>(OK);

    }

    @RequestMapping("/{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session){
        addressService.deleteByAid(
                aid,
                getUidFromSession(session),
                getUsernameFromSession(session));

        return new JsonResult<>(OK);

    }
}
