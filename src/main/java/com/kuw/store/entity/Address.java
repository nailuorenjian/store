package com.kuw.store.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

// address 类
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address extends BaseEntity implements Serializable {

    private Integer aid;
    private Integer uid;
    private String name;
    private String provinceName;
    private String provinceCode;
    private String cityName;
    private String cityCode;
    private String areaName;
    private String areaCode;
    private String zip; // 邮政编码
    private String address;
    private String phone;
    private String tel;
    private String tag;
    private Integer isDefault;

}
