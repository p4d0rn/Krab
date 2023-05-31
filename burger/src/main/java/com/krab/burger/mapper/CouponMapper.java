package com.krab.burger.mapper;

import com.krab.burger.entity.Coupon;
import com.krab.burger.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CouponMapper {
    @Select("ProductMapper/selectCoupon.sql")
    Coupon selectCoupon(@Param("couponId") Long couponId);

    @Results(id = "coupon2pro", value = {
            @Result(property = "id", column = "coupon_id"),
            @Result(property = "name", column = "coupon_name"),
            @Result(property = "image_url", column = "coupon_img"),
            @Result(property = "remark", column = "coupon_desc"),
            @Result(property = "score", column = "score"),
    })
    @Select("ProductMapper/selectAllCoupons.sql")
    List<Product> selectAllCoupons();

    @Select("ProductMapper/getAllCoupons.sql")
    List<Coupon> getAllCoupons();

    @Insert("ProductMapper/insertCoupon.sql")
    void insertCoupon(@Param("c") Coupon coupon);

    @Update("ProductMapper/editCoupon.sql")
    void editCoupon(@Param("c") Coupon coupon);

    @Delete("ProductMapper/deleteCoupon.sql")
    void delCoupons(@Param("ids") List<Long> couponIds);

    @Select("ProductMapper/coupon2dish.sql")
    Long coupon2dish(@Param("dishId") Long dishId);

    @Select("ProductMapper/coupon2user.sql")
    List<String> coupon2user(@Param("ids") List<Long> couponIds);
}
