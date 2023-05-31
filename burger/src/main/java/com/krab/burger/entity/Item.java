package com.krab.burger.entity;

import lombok.Data;

@Data
public class Item {
    private Long itemId;
    private String itemName;
    private Integer score;
    private String remark;
    private String itemImg;
}
