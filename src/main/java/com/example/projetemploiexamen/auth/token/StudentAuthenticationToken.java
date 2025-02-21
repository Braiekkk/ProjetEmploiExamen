package com.example.projetemploiexamen.auth.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class StudentAuthenticationToken extends UsernamePasswordAuthenticationToken {
    public StudentAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
