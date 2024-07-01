package co.istad.dealkh.features.auth.dto;

import lombok.Builder;

import java.util.Set;

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
 * @param accessToken  the access token issued to the user
 * @param refreshToken the refresh token issued to the user
 * @param roles       the roles of the authenticated user
 */
@Builder
public record AuthResponse(
        String accessToken,
        String refreshToken,
        Set<String> roles
) {
}
