package com.krab.burger.service;

import com.krab.burger.dto.InfoDto;
import com.krab.burger.entity.*;
import com.krab.burger.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;

    @Autowired(required = false)
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

//    @Override
//    public User login(String name, String password) {
//        User user = userMapper.searchUser(name);
//        if (user == null || !user.getPassword().equals(SecureUtil.md5(password))) {
//            return null;
//        }
//        user.setPassword(null);
//        return user;
//    }

//    @Override
//    public User register(User user) {
//        // 将当前时间戳左移32位
//        // 然后与一个随机的64位数进行或运算
//        // 生成一个长整型的ID
//        long timestamp = System.currentTimeMillis();
//        long random = new Random().nextLong();
//        long id = timestamp << 32 | (random & 0xFFFFFFFFL);
//        user.setUserId(id);
//        user.setAvatar(new byte[]{});  // 默认头像
//        user.setPoints(10);  // 初始积分为10
//        System.out.println(user);
//        userMapper.insertUser(user);
//        user.setPassword(null);
//        return user;
//    }

    @Override
    public void register(String userId) {
        userMapper.insertUser(userId);
    }

    @Override
    public User getUserByOpenid(String userId) {
        return userMapper.selectUserInfo(userId);
    }

    @Override
    public void saveUser(InfoDto infoDto, String userId) {
        userMapper.saveUser(infoDto, userId);
    }

    @Override
    public Integer checkIn(String userId) {
        return userMapper.checkIn(userId);
    }

    @Override
    public List<Address> getAddr(String userId) {
        return userMapper.getAddr(userId);
    }

    @Override
    public void setAddr(Address address) {
        if (address.getAddrId() != null) {
            List<Long> ids = userMapper.selectAddrId(address.getUserId());
            if (ids.contains(address.getAddrId())) {
                userMapper.updateAddr(address);
            }
        } else {
            userMapper.insertAddr(address);
        }
    }

    @Override
    public List<Order> getOrders(String userId) {
        userMapper.updateStatus(userId);  // 1小时自动确认订单
        return userMapper.getOrders(userId);
    }

    @Override
    public void putOrder(Order order, List<DishOrdered> dishes) {
        Long orderId = userMapper.putOrder(order);
        userMapper.putOrderDish(orderId, dishes);
        userMapper.updateOrderFee(orderId, order.getCouponId(), order.getUserId(), dishes);
        userMapper.orderBonus(orderId, order.getUserId());  // 订单积分奖励
    }

    @Override
    public List<UserCoupon> getUserCoupons(String userId) {
        return userMapper.selectUserCoupons(userId);
    }

    @Override
    public Boolean buyProduct(String userId, Long productId, Long addrId, Integer productType) {
        if(userMapper.checkPoints(userId, productId, productType) == 1){
            userMapper.buyProduct(userId, productId, addrId, productType);
            return true;
        }
        return false;
    }

    @Override
    public List<Deal> getDeals(String userId) {
        return userMapper.selectDeals(userId);
    }
}
