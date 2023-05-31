package com.krab.burger.entity;

import lombok.Data;

import java.util.List;

@Data
public class DishLabeled extends Dish{
    private List<Label> labels;
}
