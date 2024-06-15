package com.thesis.pfm.config;

import com.thesis.pfm.error.CustomOAuth2AuthenticationFailureHandler;
import com.thesis.pfm.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2AuthenticationFailureHandler customOAuth2AuthenticationFailureHandler;

    public SecurityConfiguration(CustomOAuth2UserService customOAuth2UserService,
                                 CustomOAuth2AuthenticationFailureHandler customOAuth2AuthenticationFailureHandler) {
        this.customOAuth2UserService = customOAuth2UserService;
        this.customOAuth2AuthenticationFailureHandler = customOAuth2AuthenticationFailureHandler;
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
              .authorizeHttpRequests( auth -> {
                    auth.requestMatchers("/", "/login", "/register").permitAll();
                    auth.anyRequest().authenticated();
                })
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .failureHandler(customOAuth2AuthenticationFailureHandler)
                )
               .formLogin(withDefaults())
                .build();

    }
}
