package com.krab.burger.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.krab.burger.common.PageResult;
import com.krab.burger.entity.*;
import com.krab.burger.mapper.CouponMapper;
import com.krab.burger.mapper.MenuMapper;
import com.krab.burger.mapper.UserMapper;
import com.krab.burger.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AdminServiceImpl implements AdminService {
    private final MenuMapper menuMapper;
    private final CouponMapper couponMapper;
    private final UserMapper userMapper;

    @Autowired(required = false)
    public AdminServiceImpl(MenuMapper menuMapper, CouponMapper couponMapper, UserMapper userMapper) {
        this.menuMapper = menuMapper;
        this.couponMapper = couponMapper;
        this.userMapper = userMapper;
    }


    @Override
    public Boolean login(String name, String password) {
        return Objects.equals(userMapper.adminLogin(name), password);
    }

    @Override
    public PageResult getAllCate(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Category> categories = menuMapper.selectAllCate();
        return PageUtils.getPageResult(new PageInfo<Category>(categories));
    }

    @Override
    public Category getCateById(Long cateId) {
        return menuMapper.selectCateById(cateId);
    }

    @Override
    public Boolean delCate(Long cateId) {
        if(menuMapper.selectDishByCate(cateId).size() != 0){
            return false;
        }
        menuMapper.delCateById(cateId);
        return true;
    }

    @Override
    public Category editCate(Category category) {
        menuMapper.editCate(category);
        return menuMapper.selectCateById(category.getCateId());
    }

    @Override
    public Category addCate(Category category) {
        Long cateId = menuMapper.insertCate(category);
        return menuMapper.selectCateById(cateId);
    }

    @Override
    public PageResult getAllDishes(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<DishCate> dishes = menuMapper.getAllDishes();
        return PageUtils.getPageResult(new PageInfo<DishCate>(dishes));
    }

    @Override
    public Dish getDishById(Long dishId) {
        return menuMapper.selectDishById(dishId);
    }

    @Override
    public void delDishes(List<Long> dishIds) {
        menuMapper.delDishes(dishIds);
    }

    @Override
    public void editDish(Dish dish) {
        menuMapper.updateDish(dish);
    }

    @Override
    public void addDish(Dish dish) {
        menuMapper.insertDish(dish);
    }

    @Override
    public PageResult getAllLabels(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Label> labels = menuMapper.selectAllLabels();
        return PageUtils.getPageResult(new PageInfo<Label>(labels));
    }

    @Override
    public Boolean delLabel(List<Integer> labelIds) {
        if(menuMapper.selectLabelCount(labelIds) != 0){
            return false;
        }
        menuMapper.delLabels(labelIds);
        return true;
    }

    @Override
    public void addLabel(Label label) {
        menuMapper.insertLabel(label);
    }

    @Override
    public void editLabel(Label label) {
        menuMapper.editLabel(label);
    }

    @Override
    public PageResult getAllCoupons(int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<Coupon> coupons = couponMapper.getAllCoupons();
        return PageUtils.getPageResult(new PageInfo<Coupon>(coupons));
    }

    @Override
    public Boolean delCoupons(List<Long> couponIds) {
        if(couponMapper.coupon2user(couponIds) == null){
            couponMapper.delCoupons(couponIds);
            return true;
        }
        return false;
    }

    @Override
    public Boolean addCoupon(Coupon coupon) {
        if(couponMapper.coupon2dish(coupon.getDishId()) == null){
            return false;
        }
        couponMapper.insertCoupon(coupon);
        return true;
    }

    @Override
    public Boolean editCoupon(Coupon coupon) {
        if(couponMapper.coupon2dish(coupon.getDishId()) == null){
            return false;
        }
        couponMapper.editCoupon(coupon);
        return true;
    }
}
