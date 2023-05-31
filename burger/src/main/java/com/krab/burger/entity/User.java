package com.krab.burger.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private String userId;
    private String userName;
    private String phone;
    private String avatar;
    private Integer points;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Timestamp check;
}