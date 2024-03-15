package com.kuw.store.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;


@Data
@EqualsAndHashCode
@ToString
public class Cart extends BaseEntity implements Serializable {
    //购物车的id
    private Integer cid;
    //用户的id
    private Integer uid;
    //商品的id
    private Integer pid;
    //商品在加入购物车时的价格
    private long price;
    //商品的数量
    private Integer num;
}
