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
            // OPTIONS探测报文一律放行
            return true;
        }
        String token = request.getHeader("Authorization");
        String msg;
        if (StrUtil.isBlankOrUndefined(token)) {
            msg = "请先登录";  // 请求头Authorization为空
            ResponseUtils.writeRes(response, null, false, msg);
            return false;
        } else {
            try {
                Claims claims = jwtUtils.getClaimsByToken(token);
                if (jwtUtils.isUpdateNeed(claims)) {
                    // 若剩余时间少于过期时间一半， 需要更新
                    Map<String, Object> map = MapUtil.builder(new HashMap<String, Object>())
                            .put("id", claims.get("id")).map();
                    response.setHeader("Token", jwtUtils.generateToken(map));
                }
                // Controller层通过getAttribute获取openId
                request.setAttribute("openId", claims.get("openId"));
                return true;
            } catch (Exception e) { // JWT解析失败或过期
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
