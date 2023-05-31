package com.krab.burger.interceptors;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.krab.burger.utils.JwtUtils;
import com.krab.burger.utils.ResponseUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if("OPTIONS".equals(request.getMethod().toString())){
            return true;
        }
        String token = request.getHeader("Authorization");
        String msg;
        if (StrUtil.isBlankOrUndefined(token)) {
            msg = "请先登录";
            ResponseUtils.writeRes(response, null, false, msg);
            return false;
        } else {
            try {
                Claims claims = jwtUtils.getClaimsByToken(token);
                if (jwtUtils.isUpdateNeed(claims)) {
                    Map<String, Object> map = MapUtil.builder(new HashMap<String, Object>())
                            .put("id", claims.get("id")).map();
                    response.setHeader("Token", jwtUtils.generateToken(map));
                }
                request.setAttribute("openId", claims.get("openId"));
                return true;
            } catch (Exception e) {
                if (e instanceof ExpiredJwtException) {
                    msg = "token已过期";
                } else {
                    msg = "token异常";
                }
                ResponseUtils.writeRes(response, null, false, msg);
                return false;
            }
        }
    }
}
