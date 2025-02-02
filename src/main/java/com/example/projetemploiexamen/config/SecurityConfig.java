package com.example.projetemploiexamen.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

/*
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless APIs
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS)) // Make API stateless
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/auth/**").permitAll() // Allow public access to authentication endpoints

                        .anyRequest().authenticated() // Secure all other endpoints
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // Disable HTTP Basic Auth (use JWT instead)

        return http.build();
    }
}
*/

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow public access to all endpoints
                )
                .httpBasic(httpBasic -> httpBasic.disable()); // Disable basic auth

        return http.build();
    }
}
