package com.example.demo.utillities.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.utillities.auth.PrincipalDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
public class JwtProvider {
    @Bean
    public String createAccessToken(PrincipalDetails principalDetails) {
        String accessToken = JWT.create()
                .withSubject(principalDetails.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + JwtProperties.EXPIRATION_TIME))
                .withClaim("id", principalDetails.getId())
                .withClaim("username", principalDetails.getUsername())
                .withClaim("email", principalDetails.getEmail())
                .withClaim("authorities", principalDetails.getAuthorities().toString())
                .sign(Algorithm.HMAC512(JwtProperties.SECRET));
        return accessToken;
    }
}
