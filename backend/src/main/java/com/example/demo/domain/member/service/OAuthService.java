package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.OAuthDto;

public interface OAuthService {
    String kakaoLogin(OAuthDto oAuthDto);
}
