package co.istad.dealkh.security;

import co.istad.dealkh.features.auth.dto.AuthResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * TokenGenerator is a component responsible for generating JWT access and refresh tokens.
 * It encodes JWT claims using the provided {@link JwtEncoder} instances.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Component} - Indicates that this class is a Spring component and a candidate for component scanning and dependency injection.</li>
 * <li>{@link Qualifier} - Used to distinguish beans of the same type.</li>
 * </ul>
 * </p>
 */
@Component
public class TokenGenerator {
    private final JwtEncoder jwtAccessTokenEncoder;
    private final JwtEncoder jwtRefreshTokenEncoder;

    /**
     * Constructs a new TokenGenerator with the specified JWT encoders.
     *
     * @param jwtAccessTokenEncoder  the JWT encoder for access tokens
     * @param jwtRefreshTokenEncoder the JWT encoder for refresh tokens
     */
    public TokenGenerator(
            JwtEncoder jwtAccessTokenEncoder,
            @Qualifier("jwtRefreshTokenEncoder") JwtEncoder jwtRefreshTokenEncoder
    ) {
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
        this.jwtAccessTokenEncoder = jwtAccessTokenEncoder;
    }

    /**
     * Creates a JWT access token for the given user details.
     *
     * @param customUserDetails the custom user details
     * @return the JWT access token
     */
    private String createAccessToken(CustomUserDetails customUserDetails) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.HOURS))
                .subject(customUserDetails.getEmail())
                .issuer("co.istad.dealkh")
                .claim("id", customUserDetails.getUser().getId().toString())
                .build();
        return jwtAccessTokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Creates a JWT refresh token for the given user details.
     *
     * @param customUserDetails the custom user details
     * @return the JWT refresh token
     */
    private String createRefreshToken(CustomUserDetails customUserDetails) {
        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(now.plus(7, ChronoUnit.DAYS))
                .subject(customUserDetails.getEmail())
                .issuer("co.istad.dealkh")
                .build();
        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Generates both access and refresh tokens for the authenticated user.
     *
     * @param authentication the authentication object containing the user's authentication details
     * @return the {@link AuthResponse} containing the generated tokens
     * @throws BadCredentialsException if the user is disabled
     */
    public AuthResponse generateTokens(Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        if (!customUserDetails.getUser().getIsDisabled()) {
            String refreshToken = createRefreshToken(customUserDetails);
            String accessToken = createAccessToken(customUserDetails);
            return AuthResponse.builder()
                    .refreshToken(refreshToken)
                    .accessToken(accessToken)
                    .userId(customUserDetails.getUser().getId())
                    .build();
        }
        throw new BadCredentialsException("User is disabled");
    }
}
