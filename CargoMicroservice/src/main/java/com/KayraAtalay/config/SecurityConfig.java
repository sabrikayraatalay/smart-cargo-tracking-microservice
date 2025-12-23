package com.KayraAtalay.config;

import com.KayraAtalay.jwt.JwtFilter;
import com.KayraAtalay.shared.handler.AuthEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import static com.KayraAtalay.config.RestApis.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthEntryPoint authEntryPoint;

    private static final String[] SWAGGER_WHITELIST = {
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/swagger-ui.html"
    };

    private static final String[] PUBLIC_METHODS = {
        CARGO + FIND_CARGO_BY_TRACKING_NUMBER
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(req -> req
                        .requestMatchers(SWAGGER_WHITELIST).permitAll()
                        .requestMatchers(PUBLIC_METHODS).permitAll()
                        .anyRequest().authenticated()
                )

                .exceptionHandling(e -> e
                        .authenticationEntryPoint(authEntryPoint)
                )

                .sessionManagement(s -> s
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}