package com.krab.burger.service;

import com.krab.burger.common.PageResult;
import com.krab.burger.entity.Category;
import com.krab.burger.entity.Coupon;
import com.krab.burger.entity.Dish;
import com.krab.burger.entity.Label;

import java.util.List;

public interface AdminService {
    Boolean login(String name, String password);
    PageResult getAllCate(int page, int pageSize);
    Category getCateById(Long cateId);
    Boolean delCate(Long cateId);
    Category editCate(Category category);
    Category addCate(Category category);
    PageResult getAllDishes(int page, int pageSize);
    Dish getDishById(Long dishId);
    void delDishes(List<Long> dishIds);
    void editDish(Dish dish);
    void addDish(Dish dish);
    PageResult getAllLabels(int page, int pageSize);
    Boolean delLabel(List<Integer> labelIds);
    void addLabel(Label label);
    void editLabel(Label label);
    PageResult getAllCoupons(int page, int pageSize);
    Boolean delCoupons(List<Long> couponIds);
    Boolean addCoupon(Coupon coupon);
    Boolean editCoupon(Coupon coupon);
}
