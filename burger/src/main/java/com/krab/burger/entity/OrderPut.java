package com.krab.burger.entity;

import lombok.Data;

import java.util.List;

@Data
public class OrderPut {
    List<DishOrdered> dishes;
    private String remarks;
}
