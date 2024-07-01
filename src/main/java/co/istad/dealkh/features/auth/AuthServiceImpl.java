package co.istad.dealkh.features.auth;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.auth.dto.AuthRequest;
import co.istad.dealkh.features.auth.dto.AuthResponse;
import co.istad.dealkh.features.auth.dto.RefreshTokenRequest;
import co.istad.dealkh.features.user.UserRepository;
import co.istad.dealkh.security.CustomAuthenticationProvider;
import co.istad.dealkh.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * AuthServiceImpl is a service implementation of {@link AuthService} that handles authentication
 * and token refresh operations. It uses custom authentication providers and a token generator
 * to manage authentication and token issuance.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Service} - Indicates that this class is a Spring service.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * </ul>
 * </p>
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final TokenGenerator tokenGenerator;
    private final UserRepository userRepository;


    @Qualifier("refreshTokenAuthProvider")
    private final JwtAuthenticationProvider refreshTokenAuthProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    /**
     * Authenticates a user based on the provided credentials and generates access and refresh tokens.
     *
     * @param request the authentication request containing the user's email and password
     * @return an {@link AuthResponse} containing the access and refresh tokens
     */
    @Override
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.email()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or password!"));

        if (!user.getIsVerified()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please verify your email first!");
        }

        Authentication authentication = customAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        return tokenGenerator.generateTokens(authentication);
    }

    /**
     * Refreshes the authentication tokens based on the provided refresh token.
     *
     * @param request the refresh token request containing the refresh token
     * @return an {@link AuthResponse} containing the new access and refresh tokens
     */
    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Authentication authentication = refreshTokenAuthProvider
                .authenticate(
                        new BearerTokenAuthenticationToken(request.refreshToken())
                );
        return tokenGenerator.generateTokens(authentication);
    }
}
