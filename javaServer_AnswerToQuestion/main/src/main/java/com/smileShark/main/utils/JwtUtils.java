package com.smileShark.main.utils;


import com.smileShark.main.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtils {
    public static User parseJWT(String jwt) {
        Claims claims= Jwts.parser()
                .setSigningKey("www.smileShark.com")
                .parseClaimsJws(jwt)
                .getBody();
        User user=new User();
        user.setUserId(claims.get("userId").toString());
        user.setUserPassword(claims.get("password").toString());
        user.setUserName(claims.get("userName").toString());
        return user;
    }
    public static String GenJwt(String userId,String password,String userName){
        Map<String,Object> claims=new HashMap<>();
        claims.put("userId",userId);
        claims.put("password",password);
        claims.put("userName",userName);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"www.smileShark.com")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*12))
                .compact();
    }
}

