package com.krab.burger.entity;

import lombok.Data;

import java.util.List;

@Data
public class DishCated extends Category {
    List<DishLabeled> products;
}
