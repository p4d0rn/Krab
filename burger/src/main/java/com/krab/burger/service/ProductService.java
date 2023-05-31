package com.krab.burger.service;

import com.krab.burger.entity.Coupon;
import com.krab.burger.entity.Product;

import java.util.List;

public interface ProductService {
    Coupon getCoupon(Long couponId);
    List<Product> getItemPro();
    List<Product> getCouponPro();
}
