package com.thesis.pfm.config;

import com.thesis.pfm.error.CustomOAuth2AuthenticationFailureHandler;
import com.thesis.pfm.service.CustomOAuth2UserService;
import com.thesis.pfm.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2AuthenticationFailureHandler customOAuth2AuthenticationFailureHandler;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public SecurityConfiguration(CustomOAuth2UserService customOAuth2UserService,
                                 CustomOAuth2AuthenticationFailureHandler customOAuth2AuthenticationFailureHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOAuth2AuthenticationFailureHandler = customOAuth2AuthenticationFailureHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/", "/login", "/register", "/api/auth/**", "/error", "/oauth2/authorization/google").permitAll();
                    auth.anyRequest().authenticated();
                })
                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/auth/**"))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
