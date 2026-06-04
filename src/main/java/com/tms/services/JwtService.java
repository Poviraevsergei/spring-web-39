package com.tms.services;

import ch.qos.logback.core.util.TimeUtil;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.micrometer.core.instrument.util.TimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class JwtService {

    @Value(value = "${jwt.expiration}")
    private Integer expiration;

    @Value(value = "${jwt.secret}")
    private String secret;

    //Метод генерации JWT
    public String generateJwt(String username){
        log.debug("IN JwtService:generateJwt");
        String jwt = Jwts.builder()
                .setSubject(username)
                .expiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(expiration)))
                .signWith(getSighKey())
                .compact();
        log.debug("OUT JwtService:generateJwt");
        return jwt;
    }

    private Key getSighKey(){
        log.debug("IN JwtService:getSighKey");
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        Key resultKey = Keys.hmacShaKeyFor(keyBytes);
        log.debug("OUT JwtService:getSighKey");
        return resultKey;
    }
}
