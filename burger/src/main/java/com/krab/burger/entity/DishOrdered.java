package com.krab.burger.entity;
import lombok.Data;

@Data
public class DishOrdered {
    private Dish dish;
    private Integer quantity;
}
