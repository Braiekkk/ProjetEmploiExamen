package com.example.projetemploiexamen.auth.provider;

import com.example.projetemploiexamen.auth.detailsService.AdminDetailsService;
import com.example.projetemploiexamen.auth.token.AdminAuthenticationToken;
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
public class AdminAuthenticationProvider implements AuthenticationProvider {

    private final AdminDetailsService adminUserDetailsService;
    private final PasswordEncoder passwordEncoder;

    public AdminAuthenticationProvider(AdminDetailsService adminUserDetailsService) {
        this.adminUserDetailsService = adminUserDetailsService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof AdminAuthenticationToken)) {
            return null; // Let another provider handle this request
        }

        System.out.println("You are in the admin authentication provider");

        String email = authentication.getName();
        String password = authentication.getCredentials().toString();

        // 🔹 Attempt to load user
        UserDetails user;
        try {
            user = adminUserDetailsService.loadUserByUsername(email);
            // 🔹 Check password
            if (!passwordEncoder.matches(password, user.getPassword())) {

                UsernamePasswordAuthenticationToken x =  new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
                x.setAuthenticated(false);
                return x;

            }
            // ✅ Authentication successful, return authenticated token
            System.out.println("your password verified");
            return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());

        } catch (UsernameNotFoundException e) {
            UsernamePasswordAuthenticationToken x =  new UsernamePasswordAuthenticationToken(null , null , null);
            x.setAuthenticated(false);
            return x;

        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AdminAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
