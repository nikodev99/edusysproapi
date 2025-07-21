package com.edusyspro.api.auth.token.jwt;

import com.edusyspro.api.auth.user.UserSchoolRole;
import com.edusyspro.api.model.enums.Role;
import com.edusyspro.api.auth.user.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JWTUtils {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            userClaims(claims, customUserDetails);

            claims.put("availableSchools", customUserDetails.getAvailableSchools());
            claims.put("requiresSchoolSelection ", true);
        }

        return createToken(claims, userDetails.getUsername(), jwtExpiration);
    }

    public String generateTokenWithContext(UserDetails userDetails, UserSchoolRole schoolRole) {
        Map<String, Object> claims = new HashMap<>();

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            userClaims(claims, customUserDetails);

            claims.put("schoolId", schoolRole.getSchool().getId().toString());
            List<String> authorities = schoolRole.getRoles().stream()
                    .map(Role::name)
                    .toList();

            claims.put("roles", authorities);
            claims.put("requiresSchoolSelection ", false);
        }

        return createToken(claims, userDetails.getUsername(), jwtExpiration);
    }

    public String generateRefreshToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", "refresh");

        if (userDetails instanceof CustomUserDetails customUserDetails) {
            claims.put("id", customUserDetails.getId());
        }

        return createToken(claims, userDetails.getUsername(), refreshTokenExpiration);
    }
    
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Long getUserIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return claims.get("id", Long.class);
    }

    @SuppressWarnings("unchecked")
    public List<Role> getRoleFromToken(String token){
        final Claims claims = getAllClaimsFromToken(token);
        return (List<Role>) claims.get("roles");
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    public Boolean isRefreshToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return  "refresh".equals(claims.get("type", String.class));
    }

    public String extractTokenFromHeader(String authHeader) {
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * Gets the school ID from the token.
     * Returns null if the token doesn't have school context.
     */
    public UUID getSchoolIdFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        String schoolIdStr = claims.get("schoolId", String.class);
        return schoolIdStr != null ? UUID.fromString(schoolIdStr) : null;
    }

    /**
     * Gets roles for the school from the token.
     * These are the actual Role enums for the selected school.
     */
    @SuppressWarnings("unchecked")
    public List<String> getSchoolRolesFromToken(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return (List<String>) claims.get("roles");
    }

    public Boolean requiresSchoolSelection(String token) {
        final Claims claims = getAllClaimsFromToken(token);
        return Boolean.TRUE.equals(claims.get("requiresSchoolSelection", Boolean.class));
    }

    /**
     * Generates a JWT token based on the provided claims, subject, and expiration time.
     *
     * @param claims     a map containing the claims to include in the token
     * @param subject    the subject for which the token is being created
     * @param expiration the expiration time for the token in milliseconds
     * @return the generated JWT token as a string
     */
    private String createToken(Map<String, Object> claims, String subject, Long expiration) {
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        // In JJWT 0.12.6, we need to use proper key generation
        byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(jwtSecret.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private void userClaims(Map<String, Object> claims, CustomUserDetails customUserDetails) {
        claims.put("id", customUserDetails.getId());
        claims.put("username", customUserDetails.getUsername());
        claims.put("email", customUserDetails.getEmail());
        claims.put("phoneNumber", customUserDetails.getPhoneNumber());
        claims.put("userType", customUserDetails.getUserType());
    }


}
