package com.krab.burger.service;

import com.krab.burger.entity.Dish;
import com.krab.burger.entity.DishCate;
import com.krab.burger.entity.DishCated;

import java.util.List;

public interface DishService {
    List<DishCate> getAllDishes();

    void addDish(Dish dish);

    List<DishCated> getMenu();
}
