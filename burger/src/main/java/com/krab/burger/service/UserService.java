package com.krab.burger.service;
import com.krab.burger.dto.InfoDto;
import com.krab.burger.entity.*;

import java.util.List;

public interface UserService {
    // User login(String name, String password);
    void register(String userId);
    User getUserByOpenid(String openId);
    void saveUser(InfoDto infoDto, String userId);
    Integer checkIn(String userId);
    List<Address> getAddr(String userId);
    void setAddr(Address address);
    List<Order> getOrders(String userId);
    void putOrder(Order order, List<DishOrdered> dishes);
    List<UserCoupon> getUserCoupons(String userId);
    Boolean buyProduct(String userId, Long productId, Long addrId, Integer productType);
    List<Deal> getDeals(String userId);
}
