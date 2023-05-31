package com.krab.burger.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class Deal {
    private Long dealId;
    private Long addrId;
    private Integer score;
    private String productImg;
    private Integer productId;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp dealTime;
}
