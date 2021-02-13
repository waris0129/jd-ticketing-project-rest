package com.cybertek.util;

import com.cybertek.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JWTUtil {

    @Value("${security.jwt.secret-key}")
    private String secret = "cybetek";


    public String generateToken(UserEntity user, String username){

        Map<String,Object> claims = new HashMap<>();

        claims.put("username",user.getUsername());
        claims.put("id",user.getId());
        claims.put("firstName",user.getFirstName());
        claims.put("lastName",user.getLastName());

        return createToken(claims,username);
    }

    private String createToken(Map<String,Object> claims, String username){

        String token = Jwts.builder()
                        .setClaims(claims)
                        .setSubject(username)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis()+ 1000*60*60*10))// 10 hours token validity
                        .signWith(SignatureAlgorithm.HS256,secret)
                        .compact();


        return token;
    }


    private Claims extractAllClaims(String token){

        Claims claims = Jwts.parser()
                            .setSigningKey(secret)
                            .parseClaimsJws(token)
                            .getBody();

        return claims;
    }


    private <T> T extractClaims(String token, Function<Claims,T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String extractUsername(String token){
        return extractClaims(token,Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaims(token,Claims::getExpiration);
    }


    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }





}


































