package com.example.projetemploiexamen.auth.provider;

import com.example.projetemploiexamen.auth.detailsService.StudentDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StudentAuthenticationProvider implements AuthenticationProvider {

    private final StudentDetailsService studentUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public StudentAuthenticationProvider(StudentDetailsService studentUserDetailsService) {
        this.studentUserDetailsService = studentUserDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("you are in the STUDENT authentication provider");
        System.out.println("authentication principle : " + authentication.getPrincipal());
        System.out.println("authentication name : " + authentication.getName());
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = studentUserDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid student credentials");
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
