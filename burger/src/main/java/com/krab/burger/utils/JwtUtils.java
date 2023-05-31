package com.krab.burger.utils;

import io.jsonwebtoken.*;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Data
@Component
@ConfigurationProperties(prefix = "krab.jwt")
public class JwtUtils {

    private long expire;
    private String secret;
    private String header;

    // 生成JWT
    public String generateToken(Map<String, Object> claims) {

        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + 1000 * expire);

        return Jwts.builder()
                .setClaims(claims)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(nowDate)
                .setExpiration(expireDate)    // 7天过期
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }


    // 解析JWT
    public Claims getClaimsByToken(String jwt) throws Exception {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody();
    }

    // 判断JWT是否需要更新
    public boolean isUpdateNeed(Claims claims) {
        Date expiration = claims.getExpiration();
        // 若剩余时间少于过期时间一半， 需要更新
        return (expiration.getTime() - System.currentTimeMillis()) < (expire >> 1);
    }
}