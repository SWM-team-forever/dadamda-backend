package com.forever.dadamda.config;

import com.forever.dadamda.entity.Role;
import com.forever.dadamda.filter.JwtAuthFilter;
import com.forever.dadamda.handler.OAuth2SuccessHandler;
import com.forever.dadamda.service.CustomOAuth2UserService;
import com.forever.dadamda.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final TokenService tokenService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

         http
                .csrf().disable()
                 .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                 .and()
                .headers().frameOptions().disable()
                 .and()
                    .authorizeRequests()
                        .antMatchers("/h2-console/**", "/actuator/**",
                                "/api-docs/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                        .anyRequest().authenticated()
                 .and()
                    .logout()
                        .logoutSuccessUrl("/")
                 .and()
                    .addFilterBefore(new JwtAuthFilter(tokenService),
                            UsernamePasswordAuthenticationFilter.class)
                    .oauth2Login()
                        .successHandler(oAuth2SuccessHandler)
                            .userInfoEndpoint()
                            .userService(customOAuth2UserService);

         return http.build();
    }
}