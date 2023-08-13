package com.onboarid.wanted.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;

@Component @Getter
public class JwtTokenizer {
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private int accessTokenExpiration;

    @Value("${jwt.refresh.expiration}")
    private int refreshTokenExpiration;

    public String encodeBase64SecretKey (String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken (Map<String, Object> claims,
                                       String subject,
                                       Date expiration,
                                       String encoderSecreteKey) {
        Key key = getKeyBase64EncodeKey (encoderSecreteKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }

    public String generateRefreshToken (String subject, Date expiration, String encodeSecreteKey) {
        Key key = getKeyBase64EncodeKey (encodeSecreteKey);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(expiration)
                .signWith(key)
                .compact();
    }
    public Jws<Claims> getClaims(String jws, String base64EncodedSecretKey) {
        Key key = getKeyBase64EncodeKey(base64EncodedSecretKey);

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jws);
        return claims;
    }

    public Date getTokenExpiration (int expiration) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, expiration);
        Date date = calendar.getTime();

        return date;
    }
    private Key getKeyBase64EncodeKey (String encodeSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(encodeSecretKey);
        Key key = Keys.hmacShaKeyFor(keyBytes);

        return key;
    }
}
