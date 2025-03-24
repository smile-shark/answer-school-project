package com.smileShark.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static <T> T parseJWT(String jwt, Class<T> clazz) {
        Claims claims= Jwts.parser()
                .setSigningKey("www.tool.shark.com")
                .parseClaimsJws(jwt)
                .getBody();

//        User user=new User();
//        user.setUserId(claims.get("userId").toString());
//        user.setUserPassword(claims.get("password").toString());
//        user.setUserName(claims.get("userName").toString());
        System.out.println("操作用户用户："+claims.get("userName").toString());
        return clazz.cast(claims.getSubject());
    }
    public static String createJwt(String userId,String password,String userName){
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",userId);
        claims.put("password",password);
        claims.put("userName",userName);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"www.tool.shark.com")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*12))
                .compact();
    }
}

