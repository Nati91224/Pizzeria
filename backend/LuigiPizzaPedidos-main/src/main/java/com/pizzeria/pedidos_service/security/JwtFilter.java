package com.pizzeria.pedidos_service.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JwtFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);

            try {
                Claims claims = jwtService.leerClaims(token);
                String username = claims.getSubject();
                String role = claims.get("rol", String.class); // tu claim "rol"

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    String authority = (role == null || role.isBlank()) ? null :
                            (role.startsWith("ROLE_") ? role : "ROLE_" + role);

                    List<SimpleGrantedAuthority> authorities = authority == null
                            ? List.of()
                            : List.of(new SimpleGrantedAuthority(authority));

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(username, null, authorities);

                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.info("JwtFilter: set authentication user='{}' authorities={}", username, authorities);
                } else {
                    logger.debug("JwtFilter: no authentication set for token (username null or already authenticated)");
                }
            } catch (Exception ex) {
                logger.warn("JwtFilter: token inválido para header='{}' -> {}",
                        authHeader, ex.toString());
                logger.debug("JwtFilter: stacktrace: ", ex);
            }
        } else {
            logger.debug("JwtFilter: no Authorization header for request {} {}", request.getMethod(), request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }
}