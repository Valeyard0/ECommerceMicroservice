package com.example.ProductService.Config;

import com.example.ProductService.GlobalExceptionHandler.CustomExceptionHandlers.CustomAccessDeniedHandler;
import com.example.ProductService.GlobalExceptionHandler.CustomExceptionHandlers.CustomAuthenticationEntryPoint;
import com.example.ProductService.Jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable));
        http.exceptionHandling(ex -> ex.authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()
                .requestMatchers(HttpMethod.PUT,"/api/v1/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST,"/api/v1/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/categories/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT,"/api/v1/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE,"/api/v1/products/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET,"/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.GET,"/categories/**").permitAll()
                .anyRequest()
                .authenticated());
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}