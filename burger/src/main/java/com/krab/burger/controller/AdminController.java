package com.krab.burger.controller;

import com.krab.burger.common.Result;
import com.krab.burger.dto.BatchDto;
import com.krab.burger.entity.*;
import com.krab.burger.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    private AdminService adminService;
//    @Autowired
//    private JwtUtils jwtUtils;

//    @PostMapping("/login")
//    public Result login(@RequestBody Admin admin) {
//        if(adminService.login(admin.getUsername(), admin.getPassword())){
//            String token = jwtUtils.generateToken(MapUtil.builder(new HashMap<String, Object>()).put("openId", "admin").map());
//            return Result.success(MapUtil.builder(new HashMap<String, Object>())
//                    .put("token", token).map(), "登录成功");
//        }
//        return Result.error("账号或密码错误");
//    }

    @GetMapping("/category/page")
    public Result getAllCate(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return Result.success(adminService.getAllCate(page, pageSize), "获取类型成功");
    }

    @GetMapping("/category/{cateId}")
    public Result getCateById(@PathVariable Long cateId) {
        return Result.success(adminService.getCateById(cateId), "获取类型成功");
    }

    @GetMapping("/category/del/{cateId}")
    public Result delCateById(@PathVariable Long cateId) {
        if (adminService.delCate(cateId)) {
            return Result.success(null, "删除类型成功");
        }
        return Result.error("该类型关联菜品, 无法删除");
    }

    @PostMapping("/category/edit")
    public Result editCate(@RequestBody Category category) {
        return Result.success(adminService.editCate(category), "类型更新成功");
    }

    @PostMapping("/category/add")
    public Result addCate(@RequestBody Category category) {
        return Result.success(adminService.addCate(category), "添加类型成功");
    }

    @GetMapping("/dish/page")
    public Result getAllDishes(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return Result.success(adminService.getAllDishes(page, pageSize), "获取菜品成功");
    }

    @GetMapping("/dish/{dishId}")
    public Result getDishById(@PathVariable Long dishId) {
        return Result.success(adminService.getDishById(dishId), "获取菜品成功");
    }

    @PostMapping("/dish/del")
    public Result delDishes(@RequestBody BatchDto batchDto) {
        adminService.delDishes(batchDto.getIds());
        return Result.success(null, "删除成功");
    }

    @PostMapping("/dish/edit")
    public Result editDish(@RequestBody Dish dish) {
        adminService.editDish(dish);
        return Result.success(null, "修改成功");
    }

    @PostMapping("/dish/add")
    public Result addDish(@RequestBody Dish dish) {
        dish.setDishStatus(1);
        dish.setSales(0);
        dish.setRemark("");
        adminService.addDish(dish);
        return Result.success(null, "添加成功");
    }

    @GetMapping("/label/page")
    public Result getAllLabels(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return Result.success(adminService.getAllLabels(page, pageSize), "获取标签成功");
    }

    @GetMapping("/label/del")
    public Result delLabels(@RequestParam("ids") List<Integer> labelIds) {
        if (adminService.delLabel(labelIds))
            return Result.success(null, "删除标签成功");
        return Result.error("存在关联菜品的标签, 无法删除");
    }

    @PostMapping("/label/edit")
    public Result editLabel(@RequestBody Label label) {
        adminService.editLabel(label);
        return Result.success(null, "修改成功");
    }

    @PostMapping("/label/add")
    public Result addLabel(@RequestBody Label label) {
        adminService.addLabel(label);
        return Result.success(null, "添加成功");
    }

    @GetMapping("/coupon/page")
    public Result getAllCoupons(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize) {
        return Result.success(adminService.getAllCoupons(page, pageSize), "获取优惠券成功");
    }

    @GetMapping("/coupon/del")
    public Result delCoupons(@RequestParam("ids") List<Long> couponIds) {
        if (adminService.delCoupons(couponIds))
            return Result.success(null, "删除优惠券成功");
        return Result.error("存在用户持有的优惠券, 无法删除");
    }

    @PostMapping("/coupon/edit")
    public Result editCoupon(@RequestBody Coupon coupon) {
        if (adminService.editCoupon(coupon))
            return Result.success(null, "修改成功");
        return Result.error("dishId不存在");
    }

    @PostMapping("/coupon/add")
    public Result addCoupon(@RequestBody Coupon coupon) {
        if (adminService.addCoupon(coupon))
            return Result.success(null, "添加成功");
        return Result.error("dishId不存在");
    }

}
