package com.study.SpringSecurityMybatis.security.jwt;

import com.study.SpringSecurityMybatis.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtProvider {
    private final Key key;

    public JwtProvider (@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));  // 문자열을 Byte로바꿔야만 key객체를 쓸수있음
    }

    public Date getExpireDate() {
        return new Date(new Date().getTime() + (1000l * 60 * 60 * 24 * 30));
    }

    public String generateAccessToken(User user) {

        return Jwts.builder()
                .claim("userId", user.getId())
                .expiration(getExpireDate())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String removeBearer(String bearerToken) {
        int bearerLength = "bearer ".length();
        return bearerToken.substring(bearerLength);
    }

    public Claims getClaims(String token) {
        JwtParser jwtParser = Jwts.parser()
                .setSigningKey(key)
                .build();
        return jwtParser.parseClaimsJws(token).getPayload();
    }
}
