package id.eduparx.social.config.security;

import java.util.Date;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.security.core.*;

@Component
public class JwtTokenService {
    private final SecretKey key;
    private final long expirationMillis;

    public JwtTokenService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration}") long millis) {
        this.key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMillis = millis;
    }

    public String generateToken(Authentication auth) {
        var principal = (DomainUserDetails) auth.getPrincipal();
        var now = new Date();
        var exp = new Date(now.getTime() + expirationMillis);
        var roles = principal.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toSet());

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("uid", principal.getId())
                .claim("roles", roles)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private io.jsonwebtoken.Jws<Claims> parseClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public boolean validate(String token){
        try {
            parseClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsername(String token){
        Claims claims = parseClaims(token).getBody();
        return claims.getSubject();
    }

}
