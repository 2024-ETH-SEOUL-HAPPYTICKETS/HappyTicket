package com.example.demo.utillities.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.demo.domain.member.entity.Member;
import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.utillities.auth.PrincipalDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {
    private final MemberRepository memberRepository;
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager,MemberRepository memberRepository) {
        super(authenticationManager);
        this.memberRepository = memberRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwtHeader = request.getHeader(JwtProperties.HEADER_STRING);
        if(jwtHeader == null || !jwtHeader.startsWith(JwtProperties.TOKEN_PREFIX)){
            chain.doFilter(request,response);
            return;
        }
        jwtHeader = jwtHeader.replace(JwtProperties.TOKEN_PREFIX,"");
        log.info(jwtHeader);
        try {
            Algorithm algorithm = Algorithm.HMAC512(JwtProperties.SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = jwtVerifier.verify(jwtHeader);
            Claim idClaim = decodedJWT.getClaim("id");
            Optional<Member> memberOptional = memberRepository.findById(idClaim.asInt());

            if (memberOptional.isEmpty()){
                log.warn("DB에 조회 실패");
                chain.doFilter(request,response);
                return;
            }
            PrincipalDetails principalDetails = new PrincipalDetails(memberOptional.get());

            if(decodedJWT.getExpiresAt().before(new Date())){
                log.warn("접속 토큰이 만료되었습니다.");
                chain.doFilter(request,response);
                return;
            }

            Authentication authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
            log.info("principalDetails.getUsername()={}",principalDetails.getUsername());
            log.info("principalDetails.getEmail()={}",principalDetails.getEmail());
            log.info("principalDetails.getAuthorities()={}",principalDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (Exception e) {
            log.info("유효하지 않은 토큰", e.getMessage());
            chain.doFilter(request,response);
            return;
        }

        chain.doFilter(request,response);
    }
}
