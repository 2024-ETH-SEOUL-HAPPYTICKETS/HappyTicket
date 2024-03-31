package com.example.demo.configuration;

import com.example.demo.domain.member.repository.MemberRepository;
import com.example.demo.utillities.jwt.JwtAuthenticationFilter;
import com.example.demo.utillities.jwt.JwtAuthorizationFilter;
import com.example.demo.utillities.jwt.JwtProperties;
import com.example.demo.utillities.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    @Autowired
    private MemberRepository memberRepository;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors((cors)->cors.configurationSource(corsConfigurationSource()));
        http.sessionManagement((session)->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin((formLogin)->formLogin.disable())
                .httpBasic((httpBasic)->httpBasic.disable())
                .authorizeHttpRequests((auth)->{
                    auth.requestMatchers("/**").permitAll();
                    auth.requestMatchers("/api/v1/member/all").hasAnyRole("ADMIN");
                })
                .apply(new MyCustomDSL());
        ;

        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("http://localhost:3000");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    public class MyCustomDSL extends AbstractHttpConfigurer<MyCustomDSL , HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager =
                    builder.getSharedObject(AuthenticationManager.class);
            builder
                    .addFilter(new JwtAuthenticationFilter(authenticationManager))
                    .addFilter(new JwtAuthorizationFilter(authenticationManager, memberRepository));
            super.configure(builder);
        }

    }
}
