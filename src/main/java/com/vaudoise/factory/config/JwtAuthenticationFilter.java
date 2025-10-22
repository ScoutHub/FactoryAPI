package com.vaudoise.factory.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaudoise.factory.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String USER_ID_ATTRIBUTE = "userId";

    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth/",
            "/swagger-ui",
            "/v3/api-docs",
            "/actuator"
    );

    private final ObjectMapper objectMapper;
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, ObjectMapper objectMapper) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        if (isPublicPath(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        String authHeader = request.getHeader(AUTHORIZATION_HEADER);

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            sendErrorResponse(response,
                    "Missing or invalid Authorization header");
            return;
        }

        String token = authHeader.substring(BEARER_PREFIX.length());

        try {
            if (!jwtUtil.validateToken(token)) {
                sendErrorResponse(response,
                        "Invalid or expired token");
                return;
            }

            UUID userId = jwtUtil.extractUserId(token);
            request.setAttribute(USER_ID_ATTRIBUTE, userId);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            log.error("JWT validation error for token: {}", token.substring(0, Math.min(20, token.length())), e);
            sendErrorResponse(response,
                    "Token validation failed: " + e.getMessage());
        }
    }

    private boolean isPublicPath(String path) {
        return PUBLIC_PATHS.stream().anyMatch(path::startsWith);
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        Map<String, Object> errorResponse = Map.of(
                "status", HttpServletResponse.SC_UNAUTHORIZED,
                "message", message,
                "timestamp", System.currentTimeMillis()
        );

        objectMapper.writeValue(response.getWriter(), errorResponse);
    }

}