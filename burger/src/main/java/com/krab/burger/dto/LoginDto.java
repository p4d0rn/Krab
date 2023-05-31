package com.krab.burger.dto;

import lombok.Data;

@Data
public class LoginDto {
    private String name;
    private String password;
    private String code;
    private String randId;
}