package com.krab.burger.mapper;

import com.krab.burger.entity.Product;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ItemMapper {
    @Results(id = "item2pro", value = {
            @Result(property = "id", column = "item_id"),
            @Result(property = "name", column = "item_name"),
            @Result(property = "image_url", column = "item_img"),
            @Result(property = "remark", column = "remark"),
            @Result(property = "score", column = "score"),
    })
    @Select("ProductMapper/selectAllItems.sql")
    List<Product> selectAllItems();
}
