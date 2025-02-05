package com.example.projetemploiexamen.auth.provider;

import com.example.projetemploiexamen.auth.detailsService.AdminDetailsService;
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
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private final AdminDetailsService adminUserDetailsService;
    private final PasswordEncoder passwordEncoder;
    public AdminAuthenticationProvider(AdminDetailsService adminUserDetailsService) {
        this.adminUserDetailsService = adminUserDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("you are in the admin authentication provider");
        System.out.println("authentication principle : " + authentication.getPrincipal());
        System.out.println("authentication name : " + authentication.getName());
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails user = adminUserDetailsService.loadUserByUsername(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Invalid admin credentials");
        }

        return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
