package com.edusyspro.api.auth.token.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    private final UserDetailsService userDetailsService;

    private final JWTUtils jwtUtils;

    private static final Logger logger = LoggerFactory.getLogger(JwtRequestFilter.class);

    @Autowired
    public JwtRequestFilter(UserDetailsService userDetailsService, JWTUtils jwtUtils) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                username = jwtUtils.getUsernameFromToken(jwtToken);
            }catch (IllegalArgumentException e) {
                logger.warn("Unable to get JWT Token from request");
            } catch (ExpiredJwtException e) {
                logger.warn("JWT Token has expired for user: {}", e.getClaims().getSubject());
                response.setHeader("Token-Expired", "true");
            } catch (MalformedJwtException e) {
                logger.warn("JWT Token is malformed");
            } catch (SignatureException e) {
                logger.warn("JWT Token signature validation failed");
            } catch (Exception e) {
                logger.warn("JWT Token processing error: {}", e.getMessage());
            }
        } else if (requestTokenHeader != null) {
            logger.warn("JWT token does not begin with bearer String");
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtUtils.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);

                    logger.debug("Authentication set for user: {}", username);
                }else {
                    logger.warn("JWT Token validation failed for user: {}", username);
                }
            }catch (UsernameNotFoundException e) {
                logger.warn("User not found: {}", username);
            } catch (Exception e) {
                logger.error("Authentication error for user: {}", username, e);
            }
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestUri = request.getRequestURI();

        // Log for debugging - you can remove this later
        logger.info("shouldNotFilter - checking URI: {}", requestUri);

        // Check for public endpoints that should skip JWT validation
        boolean shouldSkip = requestUri.startsWith("/api/v1/auth/") ||
                requestUri.startsWith("/api/v1/public/") ||
                requestUri.equals("/favicon.ico") ||
                requestUri.equals("/error");

        logger.info("shouldNotFilter result for {}: {}", requestUri, shouldSkip);

        return shouldSkip;
    }
}
