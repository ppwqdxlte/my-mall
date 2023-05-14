package com.laowang.mymall.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: my-mall
 * @description: 用于生成和解析JWTtoken的工具类
 * @author: Laowang
 * @create: 2023-05-14 11:58
 */
@Component
public class JwtTokenUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);
    private static final String CLAIM_KEY_USERNAME = "sub";     // 登录用户
    public static final String CLAIM_KEY_CREATED = "created";   // 创建时间
    @Value("${jwt.secret}")
    private String secret;      // JWT加密码
    @Value("${jwt.expiration}")
    private Long expiration;    // JWTtoken过期时间，单位min

    /**
     * 根据 "声明" 生成JWTtoken
     * @param claims 声明条款
     * @return token字符串
     */
    private String generateToken(Map<String,Object> claims){
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExprDate(expiration))
                .signWith(SignatureAlgorithm.HS512,secret)
                .compact();
    }

    /**
     * 生成token的过期时间
     * @return 过期时间毫秒级时间戳的Date对象
     */
    private Date generateExprDate(Long expiration){
        return new Date(System.currentTimeMillis()+expiration*1000);
    }

    /**
     * 从token加密串中解析出JWT负载
     * @param token 尚未验证的token字符串
     * @return 声明款项（JWT负载）
     */
    private Claims getClaimsFromToken(String token){
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){
            LOGGER.error("JWT格式验证失败:{}",token);
        }
        return claims;
    }

    /**
     * 从token中获取登录用户名
     * @param token token串
     * @return 用户名
     */
    public String getUsernameFromToken(String token){
        String username = null;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e){
            LOGGER.error("解析用户名失败:{}",token);
        }
        return username;
    }

    /**
     * 从token中获取过期时间
     * @param token token
     * @return 过期时间
     */
    private Date getExpiredDateFromToken(String token){
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }
    /**
     * 验证token是否已经失效
     * @param token 客户端传入的token
     * @return true-失效，false-还有效
     */
    public boolean isTokenExpired(String token){
        Date expiredDate = getExpiredDateFromToken(token);
        return expiredDate.before(new Date());
    }

    /**
     * 验证token是否还有效
     * @param token         客户端传入的token
     * @param userDetails   从数据库中查询出来的用户信息
     * @return true-有效，false-已经无效
     */
    public boolean validateToken(String token, UserDetails userDetails){
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 根据用户信息生成 JWT token
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails){
        Map<String,Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME,userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否可以被刷新
     * @param token token
     * @return true-没过期，可以被刷新；false-已过期无法刷新
     */
    public boolean canRefresh(String token){
        return !isTokenExpired(token);
    }

    /**
     * 刷新token
     * @param token 原来的旧token
     * @return 刷新的token
     */
    public String refreshToken(String token){
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED,new Date());
        return generateToken(claims);
    }
}
