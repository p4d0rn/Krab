package com.krab.burger.mapper;

import com.krab.burger.dto.InfoDto;
import com.krab.burger.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Select("UserMapper/adminLogin.sql")
    String adminLogin(@Param("name") String name);

    @Insert("UserMapper/insertUser.sql")
    void insertUser(@Param("userId") String userId);

    @Select("UserMapper/searchUser.sql")
    User searchUser(@Param("name") String name);

    @Update("UserMapper/saveUser.sql")
    void saveUser(@Param("u") InfoDto infoDto, @Param("userId")String userId);

    @Update("UserMapper/checkIn.sql")
    Integer checkIn(@Param("userId") String userId);

    @Select("UserMapper/selectAllAddr.sql")
    List<Address> getAddr(@Param("userId") String userId);

    @Insert("UserMapper/insertAddr.sql")
    void insertAddr(@Param("addr") Address address);

    @Update("UserMapper/updateAddr.sql")
    void updateAddr(@Param("addr") Address address);

    @Select("UserMapper/selectAddrId.sql")
    List<Long> selectAddrId(@Param("userId") String userId);

    @Results(id = "orderDishMap", value = {
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "addrId", column = "addr_id"),
            @Result(property = "status", column = "status"),
            @Result(property = "orderTime", column = "order_time"),
            @Result(property = "pickupNo", column = "pickup_no"),
            @Result(property = "remarks", column = "remarks"),
            @Result(property = "totalFee", column = "total_fee"),
            @Result(property = "dishes", column = "order_id", javaType = List.class, many = @Many(select = "getDishesForOrder"))
    })
    @Select("UserMapper/selectAllOrders.sql")
    List<Order> getOrders(@Param("userId") String userId);

    @Results(id = "dishOrdered", value = {
            @Result(property = "dish.dishName", column = "dish_name"),
            @Result(property = "dish.dishPrice", column = "dish_price"),
            @Result(property = "dish.dishImg", column = "dish_img"),
            @Result(property = "quantity", column = "quantity")
    })
    @Select("UserMapper/getOrderDish.sql")
    List<DishOrdered> getDishesForOrder(@Param("orderId") Long orderId);

    @Select("UserMapper/putOrder.sql")
    Long putOrder(@Param("o") Order order);

    @Insert("UserMapper/putOrderDish.sql")
    void putOrderDish(@Param("orderId") Long orderId, @Param("dishes") List<DishOrdered> dishes);

    @Update("UserMapper/updateOrderFee.sql")
    void updateOrderFee(@Param("orderId") Long orderId, @Param("couponId") Long couponId, @Param("userId") String userId, @Param("dishes") List<DishOrdered> dishes);

    @Update("UserMapper/orderBonus.sql")
    void orderBonus(@Param("orderId") Long orderId, @Param("userId") String userId);

    @Update("UserMapper/updateStatus.sql")
    void updateStatus(@Param("userId") String userId);

    @Select("UserMapper/selectUserCoupons.sql")
    List<UserCoupon> selectUserCoupons(@Param("userId") String userId);

    @Select("UserMapper/selectUserInfo.sql")
    User selectUserInfo(@Param("userId") String userId);

    @Select("UserMapper/checkPoints.sql")
    Integer checkPoints(@Param("userId") String userId, @Param("productId") Long productId, @Param("productType") Integer productType);

    @Insert("UserMapper/buyProduct.sql")
    void buyProduct(@Param("userId") String userId, @Param("productId") Long productId, @Param("addrId") Long addrId, @Param("productType") Integer productType);

    @Results(id = "selectDeals", value = {
            @Result(property = "dealId", column = "deal_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productImg", column = "product_img"),
            @Result(property = "score", column = "score"),
            @Result(property = "dealTime", column = "deal_time"),
            @Result(property = "addrId", column = "addr_id"),
    })
    @Select("UserMapper/selectDeals.sql")
    List<Deal> selectDeals(@Param("userId") String userId);

    @Select("UserMapper/test.sql")
    Integer test();
}
