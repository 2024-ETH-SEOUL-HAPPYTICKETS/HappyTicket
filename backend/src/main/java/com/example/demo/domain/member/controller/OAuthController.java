package com.example.demo.domain.member.controller;

import com.example.demo.domain.member.dto.OAuthDto;
import com.example.demo.domain.member.service.OAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("/api/v1/oauth")
public class OAuthController {
    @Autowired
    private OAuthService oAuthService;
    private

    @PostMapping
    ResponseEntity<?> oauthhandler(@RequestBody OAuthDto oAuthDto){
        log.info("oAuthDto={}", oAuthDto);
        String result = oAuthService.kakaoLogin(oAuthDto);
                return ResponseEntity.ok().body(result);

    }

}
