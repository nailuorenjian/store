package com.kuw.store.mapper;

import com.kuw.store.entity.Address;
import com.kuw.store.entity.District;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface AddressMapper {

    /**
     *  插入用户的收货地址数据
     * @param address
     * @return 受影响的行数
     */
    Integer insert(Address address);

    /**
     *  根据用户的id统计收货地址数量
     * @param uid
     * @return 当前用户收货地址的总算
     */
    Integer countByUid(Integer uid);

    /**
     *  根据用户id查询用户的收货地址
     * @param uid
     * @return
     */
    List<Address> findByUid(Integer uid);

    /**
     *  根据aid查询收货地址数据
     * @param aid
     * @return
     */
    Address findByAid(Integer aid);

    /**
     *  根据uid 把默认地址1设置为0 非默认
     * @param uid
     * @return
     */
    Integer updateNonDefault(Integer uid);

    /**
     *  根据aid 把默认地址设置为1 默认
     * @param aid
     * @return
     */
    Integer updateDefaultByAid(
                                Integer aid,
                                String modifiedUser,
                                Date modifiedTime);

    Integer deleteByAid(Integer aid);

    /**
     *  根据用户uid 查询最后一次被修改的数据
     * @param uid
     * @return
     */
    Address findLastModified(Integer uid);


}
