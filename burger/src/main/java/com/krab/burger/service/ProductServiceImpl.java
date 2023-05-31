package com.krab.burger.service;

import com.krab.burger.entity.Coupon;
import com.krab.burger.entity.Product;
import com.krab.burger.mapper.CouponMapper;
import com.krab.burger.mapper.ItemMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    private final CouponMapper couponMapper;
    private final ItemMapper itemMapper;

    @Autowired(required = false)
    public ProductServiceImpl(CouponMapper couponMapper, ItemMapper itemMapper) {
        this.couponMapper = couponMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public Coupon getCoupon(Long couponId) {
        return couponMapper.selectCoupon(couponId);
    }

    @Override
    public List<Product> getItemPro() {
        return itemMapper.selectAllItems();
    }

    @Override
    public List<Product> getCouponPro() {
        return couponMapper.selectAllCoupons();
    }
}
