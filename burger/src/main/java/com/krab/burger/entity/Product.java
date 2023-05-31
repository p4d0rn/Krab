package com.krab.burger.entity;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String image_url;
    private String remark;
    private Integer score;
}
