package com.GymMate.backend.Securitys.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET = "ZsWeJpuWnrRZfPi+DqzHrIjw0/SI70sOgcz7n+mb1JuSv8UdUMFxdeTpOc+06+L25sfND0Bwu6TIepxdImYPr4DuQx8uQT4ZMxoIXxorutir+LjDzdL9jZles9Dr2q2fqfbPvp3nD5NdIworiMssP7sPqoKZZg1Wq3K1UeBtPoEa4LCA/BBC4FREvtlQEgwPloRDP5Yd7mAVm1V8xjcFWHCBs02g0kFdg0r2UFyg9vgwoBaVCx4zewurpUr8BMWToTfKnQGGXyt9qcVMRVGIyylirf3WQml85OtUoPs2Z7fz6iINbAZ2sOzcLFDPUf60P/Cb+LdNkvzSdT6/DPdeyjbzxI1y+tigaCvPDmcIu/8=";
    private final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(Base64.getDecoder().decode(SECRET));
    
    private final int JWT_EXPIRATION =  24 * 60 * 60 * 1000; 

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String subject) {
        System.out.println(SECRET_KEY);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
