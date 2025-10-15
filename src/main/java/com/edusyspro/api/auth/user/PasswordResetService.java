package com.edusyspro.api.auth.user;

import com.edusyspro.api.auth.exception.TokenExpiredException;
import com.edusyspro.api.auth.exception.TokenNotFoundException;
import com.edusyspro.api.auth.exception.TokenUsedException;
import com.edusyspro.api.auth.response.UserInfoResponse;
import com.edusyspro.api.utils.Datetime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Autowired
    public PasswordResetService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public PasswordResetToken generatePasswordResetToken(Long userId) {
        boolean tokenExists = passwordResetTokenRepository.existsByUserId(userId);
        if (tokenExists) {
            passwordResetTokenRepository.deleteByUserId(userId);
        }

        String hashedToken = hashToken();
        //Expiry 1 hour after generation
        Instant expiry = Instant.now().plus(1, ChronoUnit.HOURS);

        PasswordResetToken resetToken = PasswordResetToken.builder()
                .token(hashedToken)
                .expiryDate(expiry)
                .user(User.builder()
                        .id(userId)
                        .build())
                .build();

        return passwordResetTokenRepository.save(resetToken);
    }

    public User validatePasswordResetToken(String token) {
        PasswordResetToken prt = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new TokenNotFoundException("Token du mot de passe invalide"));

        if (prt.isUsed()) {
            throw new TokenUsedException("Token déjà utiliser");
        }

        if (Instant.now().isAfter(prt.getExpiryDate())) {
            throw new TokenExpiredException("Token périmé le " + Datetime.instantToFormat(prt.getExpiryDate()));
        }

        return prt.getUser();
    }

    public void invalidatePasswordResetToken(String token) {
        passwordResetTokenRepository.findByToken(token)
                .ifPresent(prt -> {
                    prt.setUsed(true);
                    passwordResetTokenRepository.save(prt);
                });
    }

    private String hashToken() {
        String rawToken = generateSecureToken();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashed = md.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashed) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        }catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private String generateSecureToken() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[64];
        random.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

}
