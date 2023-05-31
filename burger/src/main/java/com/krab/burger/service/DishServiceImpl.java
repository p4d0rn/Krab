package com.krab.burger.service;

import com.krab.burger.entity.Dish;
import com.krab.burger.entity.DishCate;
import com.krab.burger.entity.DishCated;
import com.krab.burger.entity.DishLabeled;
import com.krab.burger.mapper.MenuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DishServiceImpl implements DishService{
    private final MenuMapper menuMapper;

    @Autowired(required = false)
    public DishServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    public List<DishCate> getAllDishes() {
        return menuMapper.getAllDishes();
    }
    @Override
    public void addDish(Dish dish) {
        menuMapper.insertDish(dish);
    }

    @Override
    public List<DishCated> getMenu() {
        return menuMapper.cateDish();
    }
}
