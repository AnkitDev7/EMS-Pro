package com.example.Ems_Pro.Security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.Column;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JwtGenrator {

    private static final String Key
            = "wOxKD7tHjTZlEb6fr2xzkVIb6aD7NgNCj6l9A0kJKX92leslgcY17hvLPDsG88vSKe3NZU4tU2ZwICjxmxkwcA==";

    public String GenerateToken(UserDetails userDetails) {

        System.out.println("USER = " + userDetails.getUsername());
        System.out.println("AUTHORITIES = " + userDetails.getAuthorities());

        List<String> roles = new ArrayList<>();

        for( GrantedAuthority grantedAuthority :userDetails.getAuthorities()){
            roles.add(grantedAuthority.getAuthority());
        }

        Map<String,Object> claims = new HashMap<>();
        claims.put("roles",roles);

        String token = Jwts.builder().claims(claims).subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 5 * 60 * 1000))
                .signWith(genrateKey(), Jwts.SIG.HS512)
                .compact();

        return token;
    }

    public SecretKey genrateKey() {
        SecretKey secretKey = Keys.hmacShaKeyFor(Key.getBytes());
        return secretKey;
    }

    public Claims getAllClaims(String token){
        JwtParserBuilder parser = Jwts.parser();
        Jws<Claims> signedClaims = parser.verifyWith(genrateKey()).build().parseSignedClaims(token);
        Claims payload = signedClaims.getPayload();
        return payload;
    }

    public boolean isExpired(String token){
        Claims payload = getAllClaims(token);
        Date expiration = payload.getExpiration();
        boolean before = expiration.before(new Date());
        return before;
    }

    public String getUserName(String token){
        Claims payload = getAllClaims(token);
        String userName = payload.getSubject();
        return userName;
    }

    public List<String> getRoles(String token){
        Claims payload = getAllClaims(token);
        return (List<String>) payload.get("roles");
    }
}
