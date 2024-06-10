package co.istad.dealkh.features.auth;

import co.istad.dealkh.features.auth.dto.AuthRequest;
import co.istad.dealkh.features.auth.dto.AuthResponse;
import co.istad.dealkh.features.auth.dto.RefreshTokenRequest;

/**
 * AuthService is an interface that defines the contract for authentication services.
 * It includes methods for logging in and refreshing authentication tokens.
 */
public interface AuthService {

    /**
     * Authenticates a user based on the provided credentials.
     *
     * @param request the authentication request containing the user's email and password
     * @return an {@link AuthResponse} containing the access and refresh tokens
     */
    AuthResponse login(AuthRequest request);

    /**
     * Refreshes the authentication tokens based on the provided refresh token.
     *
     * @param request the refresh token request containing the refresh token
     * @return an {@link AuthResponse} containing the new access and refresh tokens
     */
    AuthResponse refreshToken(RefreshTokenRequest request);
}
