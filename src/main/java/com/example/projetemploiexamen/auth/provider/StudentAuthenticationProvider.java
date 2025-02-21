package com.example.projetemploiexamen.auth.provider;

import com.example.projetemploiexamen.auth.detailsService.StudentDetailsService;
import com.example.projetemploiexamen.auth.token.AdminAuthenticationToken;
import com.example.projetemploiexamen.auth.token.StudentAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class StudentAuthenticationProvider implements AuthenticationProvider {

    private final StudentDetailsService studentDetailsService;
    private final PasswordEncoder passwordEncoder;

    public StudentAuthenticationProvider(StudentDetailsService studentDetailsService) {
        this.studentDetailsService = studentDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof StudentAuthenticationToken)) {
            return null; // Let another provider handle this request
        }

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 🔹 Attempt to load user
        UserDetails user;
        try {
            user = studentDetailsService.loadUserByUsername(email);
            // 🔹 Check password
            if (!passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("wrong password");
                UsernamePasswordAuthenticationToken x = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
                x.setAuthenticated(false);
                return x;

            }
            // ✅ Authentication successful, return authenticated token
            System.out.println("your password verified");
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        } catch (UsernameNotFoundException e) {
            System.out.println("student not found");
            UsernamePasswordAuthenticationToken x = new UsernamePasswordAuthenticationToken(null, null, null);
            x.setAuthenticated(false);
            return x;

        }
    }
    @Override
    public boolean supports(Class<?> authentication) {
        return StudentAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
