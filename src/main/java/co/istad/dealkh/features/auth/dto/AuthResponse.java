package co.istad.dealkh.features.auth.dto;

import lombok.Builder;

/**
 * AuthResponse is a record class that represents an authentication response containing
 * a user ID, an access token, and a refresh token.
 *
 * <p>This class uses the following annotation:
 * <ul>
 * <li>{@link Builder} - Generates a builder for this record, providing a flexible way to create instances.</li>
 * </ul>
 * </p>
 *
 * @param userId       the ID of the authenticated user
 * @param accessToken  the access token issued to the user
 * @param refreshToken the refresh token issued to the user
 */
@Builder
public record AuthResponse(
//        Long userId,
        String accessToken,
        String refreshToken
) {
}
