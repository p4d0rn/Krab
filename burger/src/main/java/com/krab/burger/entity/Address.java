package com.krab.burger.entity;

import lombok.Data;

@Data
public class Address {
    private Long addrId;
    private String userId;
    private String consignee;
    private Integer gender;  // 男0 女1
    private String consignPhone;
    private String address;  // 大概地址
    private String completeAddress;  // 详细地址
    private String detail;  // 门牌号
    private Boolean isDefault;  // 0为否、1为是 默认地址
}
