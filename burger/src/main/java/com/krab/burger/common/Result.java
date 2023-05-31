package com.krab.burger.common;

import lombok.Data;

@Data
public class Result<T>{
    private Integer code;
    private String msg;
    private T data;

    public static <T> Result<T> success(T object, String msg){
        Result<T> r = new Result<T>();
        r.data = object;
        r.code = 1;
        r.msg = msg;
        return r;
    }

    public static <T> Result<T> error(String msg){
        Result r = new Result();
        r.msg = msg;
        r.code = 0;
        return r;
    }
}
