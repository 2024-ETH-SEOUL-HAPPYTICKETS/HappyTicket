package com.example.demo.utillities.jwt;

public class JwtProperties {
    static String SECRET = "testsecretkey";
    static long EXPIRATION_TIME = 1000*60*100;
    static String TOKEN_PREFIX = "Bearer ";
    static String HEADER_STRING = "Authorization";
    static long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7;
}