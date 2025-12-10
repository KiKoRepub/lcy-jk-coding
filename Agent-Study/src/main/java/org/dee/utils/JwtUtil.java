package org.dee.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;


public class JwtUtil {


    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private static final long ACCESS_EXPIRE = 1000 * 60 * 15; // 15 分钟
    private static final long REFRESH_EXPIRE = 1000L * 60 * 60 * 24; // 24 小时


    public static String generateAccess(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roles)
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRE))
                .signWith(key)
                .compact();
    }


    public static String generateRefresh(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE))
                .signWith(key)
                .compact();
    }


    public static Claims parse(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }
}