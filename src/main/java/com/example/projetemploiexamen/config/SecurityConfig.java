package com.example.projetemploiexamen.config;


import com.example.projetemploiexamen.auth.detailsService.AdminDetailsService;
import com.example.projetemploiexamen.auth.detailsService.StudentDetailsService;
import com.example.projetemploiexamen.auth.provider.AdminAuthenticationProvider;
import com.example.projetemploiexamen.auth.provider.StudentAuthenticationProvider;
import com.example.projetemploiexamen.security.AuthFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final AuthFilter authFilter;
    private final AdminAuthenticationProvider adminAuthenticationProvider;
    private final StudentAuthenticationProvider studentAuthenticationProvider;
    public SecurityConfig(AuthFilter authFilter, AdminAuthenticationProvider adminAuthenticationProvider, StudentAuthenticationProvider studentAuthenticationProvider) {
        this.authFilter = authFilter;
        this.studentAuthenticationProvider = studentAuthenticationProvider;
        this.adminAuthenticationProvider = adminAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // Public authentication routes
                        .requestMatchers("/generateToken").permitAll() // Public endpoint for token generation
                        .requestMatchers("/admin/**").hasAuthority("ADMIN") //
                        .requestMatchers("/student/**").hasAuthority("STUDENT")
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // No sessions
                )
                .authenticationProvider(adminAuthenticationProvider)
                .authenticationProvider(studentAuthenticationProvider)
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class); // ✅ Ensure AuthFilter is applied

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(adminAuthenticationProvider) // ✅ Admin provider
                .authenticationProvider(studentAuthenticationProvider) // ✅ Student provider
                .build();
    }
}
