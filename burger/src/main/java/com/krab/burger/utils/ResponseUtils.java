package com.krab.burger.utils;

import cn.hutool.json.JSONUtil;
import com.krab.burger.common.Result;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResponseUtils {
    public static void writeRes(ServletResponse response, Object o, Boolean success, String msg) {
        response.setContentType("application/json;charset=UTF-8");
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            Result result;
            if (success) {
                result = Result.success(o, msg);
            } else {
                result = Result.error(msg);
            }
            outputStream.write(JSONUtil.toJsonStr(result).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
