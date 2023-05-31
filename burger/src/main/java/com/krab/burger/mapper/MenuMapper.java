package com.krab.burger.mapper;

import com.krab.burger.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface MenuMapper {
    @Select("MenuMapper/selectAllCate.sql")
    List<Category> selectAllCate();

    @Select("MenuMapper/selectCateById.sql")
    Category selectCateById(@Param("cateId") Long cateId);


    @Delete("MenuMapper/delCateById.sql")
    void delCateById(@Param("cateId") Long cateId);

    @Update("MenuMapper/editCate.sql")
    void editCate(@Param("c") Category category);

    @Insert("MenuMapper/insertCate.sql")
    Long insertCate(@Param("c") Category category);


    @Select("MenuMapper/selectAllDish.sql")
    List<DishCate> getAllDishes();

    @Select("MenuMapper/selectDishById.sql")
    Dish selectDishById(@Param("dishId") Long dishId);

    @Select("MenuMapper/selectDishByCate.sql")
    List<Dish> selectDishByCate(@Param("cateId") Long cateId);

    @Insert("MenuMapper/insertDish.sql")
    void insertDish(@Param("d") Dish dish);

    @Delete("MenuMapper/deleteDishes.sql")
    void delDishes(@Param("ids") List<Long> ids);

    @Update("MenuMapper/editDish.sql")
    void updateDish(@Param("d") Dish dish);

    @Update("MenuMapper/updateDishRemark.sql")
    void updateDishRemark();

    @Update("MenuMapper/updateDishSale.sql")
    void updateDishSale();

    @Select("MenuMapper/selectAllLabel.sql")
    List<Label> selectAllLabels();

    @Delete("MenuMapper/deleteLabels.sql")
    void delLabels(@Param("ids") List<Integer> ids);

    @Select("MenuMapper/selectLabelCount.sql")
    int selectLabelCount(@Param("ids") List<Integer> labelIds);

    @Insert("MenuMapper/insertLabel.sql")
    void insertLabel(@Param("l") Label label);

    @Update("MenuMapper/editLabel.sql")
    void editLabel(@Param("l") Label label);

    @Results(id = "dishCateMap", value = {
            @Result(property = "cateId", column = "cate_id"),
            @Result(property = "cateName", column = "cate_name"),
            @Result(property = "cateImg", column = "cate_img"),
            @Result(property = "products", column = "cate_id", javaType = List.class, many = @Many(select = "labelDish"))
    })
    @Select("MenuMapper/selectAllCate.sql")
    List<DishCated> cateDish();

    @Results(id = "dishLabelMap", value = {
            @Result(property = "dishId", column = "dish_id"),
            @Result(property = "cateId", column = "cate_id"),
            @Result(property = "dishName", column = "dish_name"),
            @Result(property = "dishPrice", column = "dish_price"),
            @Result(property = "dishImg", column = "dish_img"),
            @Result(property = "dishStatus", column = "dish_status"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "labels", column = "dish_id", javaType = List.class, many = @Many(select = "getLabelsForDish"))
    })
    @Select("MenuMapper/selectDishByCate.sql")
    List<DishLabeled> labelDish(@Param("cateId") Long cateId);

    @Select("MenuMapper/getDishLabel.sql")
    List<Label> getLabelsForDish(@Param("dishId") Long dishId);
}
