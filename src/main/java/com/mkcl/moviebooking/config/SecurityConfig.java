package com.mkcl.moviebooking.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(
                    SessionCreationPolicy.STATELESS
                )
            )

            .authorizeHttpRequests(auth -> auth

                // Public APIs
                .requestMatchers(
                        "/auth/**",
                        "/swagger-ui/**",
                        "/v3/api-docs/**"
                ).permitAll()

                // Movie APIs
                .requestMatchers(
                        HttpMethod.POST,
                        "/movies/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/movies/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/movies/**"
                ).hasRole("ADMIN")

                // Show APIs
                .requestMatchers(
                        HttpMethod.POST,
                        "/shows/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.PUT,
                        "/shows/**"
                ).hasRole("ADMIN")

                .requestMatchers(
                        HttpMethod.DELETE,
                        "/shows/**"
                ).hasRole("ADMIN")

                // Public Read APIs
                .requestMatchers(
                        HttpMethod.GET,
                        "/movies/**",
                        "/shows/**"
                ).permitAll()

                .requestMatchers(
                        HttpMethod.POST,
                        "/bookings/**"
                ).hasRole("CUSTOMER")

                .requestMatchers(
                        HttpMethod.GET,
                        "/bookings/**"
                ).hasRole("CUSTOMER")
                
                .requestMatchers(
                        HttpMethod.DELETE,
                        "/bookings/**"
                ).hasRole("CUSTOMER")
                
                .anyRequest()
                .authenticated()
            )

            .addFilterBefore(
                    jwtFilter,
                    UsernamePasswordAuthenticationFilter.class
            );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration
    ) throws Exception {

        return configuration.getAuthenticationManager();
    }
}