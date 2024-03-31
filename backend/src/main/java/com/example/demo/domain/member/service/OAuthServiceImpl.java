package com.example.demo.domain.member.service;

import com.example.demo.domain.member.dto.KakaoMemberInfo;
import com.example.demo.domain.member.dto.KakaoOAuth;
import com.example.demo.domain.member.dto.OAuthDto;
import com.example.demo.domain.member.dto.SignUpDto;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.utillities.auth.PrincipalDetails;
import com.example.demo.utillities.jwt.JwtProvider;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class OAuthServiceImpl implements OAuthService{
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    JwtProvider jwtProvider = new JwtProvider();

    @Override
    @Transactional
    public String kakaoLogin(OAuthDto oAuthDto) {
        String postURL = "https://kauth.kakao.com/oauth/token";
        String grant_type = "authorization_code";
        String client_id = "277a841600aba14020ce1835979cd54a";
        String redirect_uri = "http://localhost:3000";
        String code = oAuthDto.getKakaoCode();
        String client_secret = "---secret key ---";

        log.info("kakaoCode = {}", code);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", grant_type);
        requestBody.add("client_id", client_id);
        requestBody.add("redirect_uri", redirect_uri);
        requestBody.add("code", code);
        requestBody.add("client_secret",client_secret);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        log.info("requestEntity = {}", requestEntity);

        ResponseEntity<KakaoOAuth> kakaoOAuthResponseEntity
                = restTemplate.exchange(
                postURL,
                HttpMethod.POST,
                requestEntity,
                KakaoOAuth.class
        );

        log.info("Response Status Code: {}", kakaoOAuthResponseEntity.getStatusCode());
        log.info("Response Body: {}", kakaoOAuthResponseEntity.getBody());

        String accessToken = kakaoOAuthResponseEntity.getBody().getAccess_token();
        log.info("accessToken = {}",accessToken);
        KakaoMemberInfo kakaoMemberInfo = getKakaoMemberInfo(accessToken);

        log.info("kakaoMemberInfo = {}",kakaoMemberInfo);

        String username = kakaoMemberInfo.getKakao_account().getProfile().getNickname();
        String email = kakaoMemberInfo.getKakao_account().getEmail();
        Optional<Member> optionalMember = memberRepository.findMemberByEmail(email);
        Member member = null;

        SignUpDto signUpDto = SignUpDto.builder()
                .username(username)
                .password(username)
                .email(email)
                .build();


        if (optionalMember.isEmpty()) {
            memberService.signUp(signUpDto);
            Optional<Member> _member = memberRepository.findMemberByEmail(email);
            member = _member.get();
        } else {
            member = optionalMember.get();
        }

        String jwtAccessToken = jwtProvider.createAccessToken(new PrincipalDetails(member));

        return jwtAccessToken;
    }

    private KakaoMemberInfo getKakaoMemberInfo(String accessToken) {
        String userInfoUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("Authorization", "Bearer "+accessToken);
        HttpEntity httpEntity = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> resp = restTemplate.exchange(userInfoUrl , HttpMethod.POST , httpEntity, String.class);
        log.info("info={}", resp);

        Gson gson = new Gson();
        KakaoMemberInfo kaKaoMemberInfo = gson.fromJson(resp.getBody(), KakaoMemberInfo.class);

        log.info("kaKaoMember={}", kaKaoMemberInfo);

        return kaKaoMemberInfo;

    }
}
