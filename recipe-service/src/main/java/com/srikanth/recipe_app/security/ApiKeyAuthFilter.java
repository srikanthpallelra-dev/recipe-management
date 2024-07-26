package com.srikanth.recipe_app.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiKeyAuthFilter extends OncePerRequestFilter {

    // Our custom authentication filter
    // Sets the authentication fetched from request to Spring Security Context
    // Authenticates request and allows the request to next filter
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        ApiKeyAuthExtractor.extract(request)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        filterChain.doFilter(request, response);
    }
}
