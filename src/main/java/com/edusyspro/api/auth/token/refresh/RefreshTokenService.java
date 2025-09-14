package com.edusyspro.api.auth.token.refresh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshRepository refreshRepository;

    @Value("${jwt.refresh.expiration}")
    private Long refreshTokenExpiration;

    @Autowired
    public RefreshTokenService(RefreshRepository refreshRepository) {
        this.refreshRepository = refreshRepository;
    }

    public RefreshEntity saveRefreshToken(RefreshEntity refreshToken) {
        refreshRepository.deleteByUserIdAndUserAgentAndClientIp(
                refreshToken.getUserId(),
                refreshToken.getUserAgent(),
                refreshToken.getClientIp()
        );

        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));

        return refreshRepository.save(refreshToken);
    }

    public RefreshEntity findAndValidateRefreshToken (String refreshToken) {
        Optional<RefreshEntity> refreshEntity = refreshRepository.findByRefreshTokenAndIsActiveTrue(refreshToken);

        if (refreshEntity.isEmpty()) {
            return null;
        }

        RefreshEntity token = refreshEntity.get();

        if (refreshEntity.get().getExpiryDate().isBefore(Instant.now())) {
            token.setActive(false);
            refreshRepository.save(token);
            return null;
        }

        token.setLastUsedAt(Instant.now());

        return refreshRepository.save(token);
    }

    public RefreshEntity findByUserAndClientType(Long userId, String userAgent) {
        Optional<RefreshEntity> refreshEntity = refreshRepository.findByUserIdAndClientTypeAndIsActiveTrue(userId, userAgent);

        if (refreshEntity.isEmpty()) {
            return null;
        }

        RefreshEntity token = refreshEntity.get();

        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshRepository.deleteById(token.getId());
            return null;
        }

        token.setLastUsedAt(Instant.now());

        return refreshRepository.save(token);
    }

    public List<UserLoginResponse> findUserActiveLogins(long userId) {
        return refreshRepository.findAllActiveTokensByUserId(userId);
    }

    public String rotateRefreshToken(String oldRefreshToken, String newRefreshToken) {
        RefreshEntity refreshEntity = refreshRepository.findByRefreshTokenAndIsActiveTrue(oldRefreshToken).orElse(null);

        if (refreshEntity == null) {
            return null;
        }

        refreshEntity.setRefreshToken(newRefreshToken);
        refreshEntity.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshRepository.save(refreshEntity);

        return newRefreshToken;
    }

    public void revokeRefreshToken(String tokenValue) {
        refreshRepository.findByRefreshTokenAndIsActiveTrue(tokenValue)
                .ifPresent(refreshEntity -> {
                    refreshEntity.setActive(false);
                    refreshEntity.setRevokedAt(Instant.now());
                    refreshRepository.save(refreshEntity);
                });
    }

    public void revokeAllUserTokens(Long userId) {
        refreshRepository.findByUserIdAndIsActiveTrue(userId)
                .forEach(refreshEntity -> {
                    refreshEntity.setActive(false);
                    refreshEntity.setRevokedAt(Instant.now());
                    refreshRepository.save(refreshEntity);
                });
    }

    public void cleanupExpiryTokens() {
        refreshRepository.deleteByExpiryDateBefore(Instant.now());
    }
}
