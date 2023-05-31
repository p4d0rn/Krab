package com.krab.burger.dto;

import lombok.Data;

import java.util.List;

@Data
public class BatchDto {
    private List<Long> ids;
}
