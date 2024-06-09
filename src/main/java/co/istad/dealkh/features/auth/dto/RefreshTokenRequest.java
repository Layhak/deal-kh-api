package co.istad.dealkh.features.auth.dto;

import lombok.Builder;

/**
 * RefreshTokenRequest is a record class that represents a request to refresh an authentication token.
 * It contains a single field for the refresh token.
 *
 * <p>This class uses the following annotation:
 * <ul>
 * <li>{@link Builder} - Generates a builder for this record, providing a flexible way to create instances.</li>
 * </ul>
 * </p>
 *
 * @param refreshToken the refresh token used to obtain a new access token
 */
@Builder
public record RefreshTokenRequest(
        String refreshToken
) {
}
