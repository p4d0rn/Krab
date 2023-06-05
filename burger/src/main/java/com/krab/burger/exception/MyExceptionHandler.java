package com.krab.burger.exception;

import com.krab.burger.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result<String> handleException(Exception e) {
        System.out.println("异常捕获 > " + e);
        return Result.error("Oops! 出错了");
    }
}
