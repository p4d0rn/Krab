package com.krab.burger.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Order {
    private Long orderId;
    private String userId;
    private Long addrId;
    private Long couponId;
    private Integer status;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp orderTime;
    private Integer pickupNo;
    private String remarks;
    private Integer totalFee;
    private List<DishOrdered> dishes;
}
