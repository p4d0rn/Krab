package com.krab.burger.entity;

import lombok.Data;

@Data
public class Dish {
    private Long dishId;
    private Long cateId;
    private String dishName;
    private String description;
    private Integer dishPrice;
    private String dishImg;
    private Integer dishStatus; //0 停售 1 起售
    private Integer sales;
    private String remark;
}
