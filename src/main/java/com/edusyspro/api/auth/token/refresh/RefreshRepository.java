package com.edusyspro.api.auth.token.refresh;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshRepository extends JpaRepository<RefreshEntity, Long> {

    /**
     * Retrieves a {@link RefreshEntity} based on the provided refresh token.
     *
     * @param refreshToken the refresh token to search for in the database
     * @return an {@code Optional} containing the found {@link RefreshEntity},
     *         or an empty {@code Optional} if no entity is found
     */
    @Query("SELECT re FROM RefreshEntity re WHERE re.refreshToken = :refreshToken AND re.isActive = true")
    Optional<RefreshEntity> findByRefreshTokenAndIsActiveTrue(@Param("refreshToken") String refreshToken);

    /**
     * Retrieves an {@link Optional} of {@link RefreshEntity} based on the provided user ID,
     * client type, and a condition that the token is active.
     *
     * @param userId the ID of the user associated with the refresh token
     * @param clientType the type of client (e.g., web, mobile) associated with the refresh token
     * @return an {@code Optional} containing the found {@link RefreshEntity}, or an empty {@code Optional} if no entity is found
     */
    @Query("SELECT re FROM RefreshEntity re WHERE re.userId = :userId AND re.clientType = :clientType AND re.isActive = true")
    Optional<RefreshEntity> findByUserIdAndClientTypeAndIsActiveTrue(@Param("userId") Long userId, @Param("clientType") String clientType);

    /**
     * Retrieves a list of {@link RefreshEntity} instances associated with the provided user ID.
     *
     * @param userId the ID of the user for which refresh tokens are being retrieved
     * @return a list of {@link RefreshEntity} instances corresponding to the specified user ID,
     *         or an empty list if no tokens are found
     */
    @Query("SELECT re FROM RefreshEntity re WHERE re.userId = :userId AND re.isActive = true")
    List<RefreshEntity> findByUserIdAndIsActiveTrue(@Param("userId") Long userId);

    @Query("""
        SELECT new com.edusyspro.api.auth.token.refresh.UserLoginResponse(re.id, re.clientIp, re.createdAt, re.deviceFingerprint,
        re.clientType) FROM RefreshEntity re WHERE re.userId = :userId AND re.isActive = true
    """)
    List<UserLoginResponse> findAllActiveTokensByUserId(@Param("userId") Long userId);

    /**
     * Deletes a refresh token entity based on the provided user ID, user agent, and client IP address.
     *
     * @param userId the ID of the user whose refresh token is to be deleted
     * @param userAgent the user agent string associated with the refresh token
     * @param clientIp the client IP address tied to the refresh token
     */
    void deleteByUserIdAndUserAgentAndClientIp(Long userId, String userAgent, String clientIp);

    /**
     * Deletes all {@link RefreshEntity} instances from the database
     * where the expiry date is before the specified date.
     *
     * @param expiryDate the cutoff {@link Instant} date; all refresh tokens
     *                   with an expiry date earlier than this will be removed
     */
    void deleteByExpiryDateBefore(Instant expiryDate);

    /**
     * Retrieves a list of {@link RefreshEntity} instances that have not been used since a specified cutoff date
     * and are still marked as active.
     *
     * @param cutoffDate the {@link Instant} representing the cutoff date; refresh tokens with a last used date
     *                   earlier than this will be considered stale
     * @return a list of {@link RefreshEntity} instances meeting the criteria, or an empty list if no stale
     *         tokens are found
     */
    @Query("SELECT re FROM RefreshEntity re WHERE re.lastUsedAt < :cutoffDate AND re.isActive = true")
    List<RefreshEntity> findStaleTokens(@Param("cutoffDate") Instant cutoffDate);

    /**
     * Counts the number of active refresh tokens associated with a specific user.
     *
     * @param userId the ID of the user whose active refresh tokens are being counted
     * @return the number of active refresh tokens associated with the specified user
     */
    @Query("SELECT COUNT(re) FROM RefreshEntity re WHERE re.userId = :userId AND re.isActive = true")
    Long countActiveTokensByUserId(@Param("userId") Long userId);

    /**
     * Retrieves a list of {@link RefreshEntity} instances that match the given user ID and client IP address
     * and are currently marked as active.
     *
     * @param userId the ID of the user for which refresh tokens are being retrieved
     * @param clientIp the client IP address associated with the refresh tokens
     * @return a list of {@link RefreshEntity} instances that match the criteria, or an empty list if no matches are found
     */
    @Query("SELECT re FROM RefreshEntity re WHERE re.userId = ?1 AND re.clientIp = ?2 AND re.isActive = true")
    List<RefreshEntity> findUserAndClientIpAndIsActiveTrue(Long userId, String clientIp);
}
