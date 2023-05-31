package com.krab.burger.entity;

import lombok.Data;

@Data
public class Coupon {
    private Long couponId;
    private String couponName;
    private String couponDesc;
    private String couponImg;
    private Integer cut;
    private Long dishId;
    private Integer score;
}
