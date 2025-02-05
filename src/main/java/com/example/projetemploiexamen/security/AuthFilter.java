package com.example.projetemploiexamen.security;

import com.example.projetemploiexamen.admin.AdminRepository;
import com.example.projetemploiexamen.auth.detailsService.AdminDetailsService;
import com.example.projetemploiexamen.auth.detailsService.StudentDetailsService;
import com.example.projetemploiexamen.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class AuthFilter extends OncePerRequestFilter {
    private JwtUtil jwtUtil;
    private StudentDetailsService studentDetailsService;
    private AdminDetailsService adminDetailsService;


    public AuthFilter(JwtUtil jwtUtil, AdminDetailsService adminDetailsService, StudentDetailsService studentDetailsService) {
        this.jwtUtil = jwtUtil;
        this.adminDetailsService = adminDetailsService;
        this.studentDetailsService = studentDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        System.out.println("you are inside the filter");
        String requestPath = request.getServletPath();

        // ✅ Skip authentication for public routes
        if (requestPath.equals("/generateToken") || requestPath.startsWith("/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;
        String role = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractEmail(token);
            role = jwtUtil.extractRole(token); // Extract role from JWT
        }

        // ✅ 2. Authenticate user only if a valid token exists and not already authenticated
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = null;

            // ✅ 3. Load user from the correct service based on role
            if ("ADMIN".equals(role)) {
                userDetails = adminDetailsService.loadUserByUsername(email);
            } else if ("STUDENT".equals(role)) {
                userDetails = studentDetailsService.loadUserByUsername(email);
            }

            // ✅ 4. Validate the token and set authentication
            if (userDetails != null && jwtUtil.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // ✅ 5. Continue filter chain
        filterChain.doFilter(request, response);
    }




}
