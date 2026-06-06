package org.delcom.app.configs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.delcom.app.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String uri = request.getRequestURI();
        log.info("JwtAuthFilter hit: {} {}", request.getMethod(), uri);

        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (JwtUtil.validateToken(token, false)) {
                UUID userId = JwtUtil.getUserIdFromToken(token);
                String role = JwtUtil.extractRole(token);
                if (userId != null && role != null) {
                    UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                            userId.toString(), null,
                            List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                    log.info("Authenticated userId={} role={}", userId, role);
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}