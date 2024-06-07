package co.istad.dealkh.features.auth;

import co.istad.dealkh.features.auth.dto.AuthRequest;
import co.istad.dealkh.features.auth.dto.AuthResponse;
import co.istad.dealkh.features.auth.dto.RefreshTokenRequest;
import co.istad.dealkh.security.CustomAuthenticationProvider;
import co.istad.dealkh.security.TokenGenerator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final TokenGenerator tokenGenerator;

    @Qualifier("accessTokenAuthProvider")
    private final JwtAuthenticationProvider accessTokenAuthProvider;

    @Qualifier("refreshTokenAuthProvider")
    private final JwtAuthenticationProvider refreshTokenAuthProvider;

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Override
    public AuthResponse login(AuthRequest request) {
        logger.info("Attempting to authenticate user with email: {}", request.email());
        Authentication authentication = customAuthenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );
        logger.info("User authenticated successfully");
        return tokenGenerator.generateTokens(authentication);
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequest request) {
        Authentication authentication = refreshTokenAuthProvider
                .authenticate(
                        new BearerTokenAuthenticationToken(request.refreshToken())
                );
        return tokenGenerator.generateTokens(authentication);
    }
}
