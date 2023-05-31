package com.krab.burger.controller;

import cn.hutool.core.map.MapUtil;
import com.krab.burger.common.Result;
import com.krab.burger.dto.InfoDto;
import com.krab.burger.entity.*;
import com.krab.burger.service.DishService;
import com.krab.burger.service.ProductService;
import com.krab.burger.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
public class UserController {
    @Autowired
    private DishService dishService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @GetMapping("checkin")
    public Result checkIn(HttpServletRequest request) {
        if(userService.checkIn((String)request.getAttribute("openId")) == 1){
            return Result.success(true, "签到成功");
        }
        return Result.error("今日已签到");
    }

    @PostMapping("/user/save")
    public Result saveInfo(@RequestBody InfoDto infoDto, HttpServletRequest request){
        userService.saveUser(infoDto, (String)request.getAttribute("openId"));
        return Result.success(null,"保存成功");
    }

    @GetMapping("/menu")
    public Result menu() {
        return Result.success(dishService.getMenu(), "获取菜单成功");
    }

    @GetMapping("/addr/get")
    public Result getAddr(HttpServletRequest request) {
        return Result.success(userService.getAddr((String)request.getAttribute("openId")), "获取地址成功");
    }

    @PostMapping("/addr/add")
    public Result addAddr(@RequestBody Address address, HttpServletRequest request) {
        address.setUserId((String) request.getAttribute("openId"));
        userService.setAddr(address);
        return Result.success(null, "添加地址成功");
    }

    @GetMapping("/order/get")
    public Result getOrder(HttpServletRequest request) {
        return Result.success(userService.getOrders((String)request.getAttribute("openId")), "历史订单获取成功");
    }

    @PostMapping("/order/put")
    public Result putOrder(@RequestParam(value = "addr")Long addrId, @RequestParam(value = "coupon")Long couponId, @RequestBody OrderPut orderPut, HttpServletRequest request) {
        List<DishOrdered> dishes = orderPut.getDishes();
        if(dishes!=null) {
            Order order = new Order();
            order.setDishes(dishes);
            order.setRemarks(orderPut.getRemarks());
            order.setAddrId(addrId);
            order.setCouponId(couponId);
            order.setUserId((String)request.getAttribute("openId"));
            userService.putOrder(order, dishes);
            return Result.success(order, "订单提交成功");
        }
        return Result.error("商品为空");
    }

    @GetMapping("/coupon/get")
    public Result coupons(HttpServletRequest request){
        return Result.success(userService.getUserCoupons((String)request.getAttribute("openId")), "优惠券获取成功");
    }

    @GetMapping("/coupon")
    public Result coupon(@RequestParam(value = "id")Long couponId){
        return Result.success(productService.getCoupon(couponId), "优惠券信息获取成功");
    }

    @GetMapping("/products/get")
    public Result getProducts() {
        Map<String, Object> products = MapUtil.builder(new HashMap<String, Object>())
                .put("items", productService.getItemPro())
                .put("coupons", productService.getCouponPro())
                .map();
        return Result.success(products, "获取积分商品成功");
    }

    @GetMapping("/products/buy")
    public Result buyProduct(@RequestParam(value = "id")Long productId, @RequestParam(value = "type")Integer productType, @RequestParam(value = "addr")Long addrId, HttpServletRequest request) {
        if(!userService.buyProduct((String)request.getAttribute("openId"), productId, addrId, productType)){
            return Result.error("积分不足");
        } else {
            return Result.success(null, "兑换成功");
        }
    }

    @GetMapping("/products/order")
    public Result productOrder(HttpServletRequest request){
        return Result.success(userService.getDeals((String)request.getAttribute("openId")), "获取积分订单成功");
    }

}
