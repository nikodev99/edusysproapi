package com.edusyspro.api.auth.token.jwt;

import com.edusyspro.api.controller.utils.ControllerUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtAuthentificationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthentificationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String clientIp = getClientIpAddress(request);
        String userAgent = request.getHeader("User-Agent");
        String requestUri = request.getRequestURI();

        logger.warn(
                "Unauthorized access attempt - IP: {}, URI: {}, UserAgent: {}, Error: {}",
                clientIp, requestUri, userAgent, authException.getMessage()
        );

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", Instant.now().toString());
        errorResponse.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", authException.getMessage());
        errorResponse.put("path", requestUri);

        if ("true".equals(response.getHeader("Token-Expired"))) {
            errorResponse.put("code", "TOKEN_EXPIRED");
            errorResponse.put("message", "Your Session has been expired. Please login again.");
        }

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(errorResponse));
    }

    public String getClientIpAddress(HttpServletRequest request) {
        return ControllerUtils.getClientIpAddress(request);
    }
}
