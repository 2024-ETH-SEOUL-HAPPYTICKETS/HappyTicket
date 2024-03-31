package com.example.demo.utillities.jwt;

import com.example.demo.domain.member.dto.LoginDto;
import com.example.demo.utillities.auth.PrincipalDetails;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider = new JwtProvider();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Gson gson = new Gson();
        String username=null;
        String password=null;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()))) {
            LoginDto loginDto = gson.fromJson(reader, LoginDto.class);
            log.info(loginDto.toString());
            username = loginDto.getUsername();
            password = loginDto.getPassword();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info(username);
        log.info(password);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        log.info("authenticationToken = {} ",authenticationToken);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        return authentication;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("successfulAuthentication autResult= {}",authResult);
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();
        String accessToken = jwtProvider.createAccessToken(principalDetails);
        response.addHeader(JwtProperties.HEADER_STRING,JwtProperties.TOKEN_PREFIX+accessToken);
    }
}