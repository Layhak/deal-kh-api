package co.istad.dealkh.features.auth;

import co.istad.dealkh.features.auth.dto.AuthRequest;
import co.istad.dealkh.features.auth.dto.AuthResponse;
import co.istad.dealkh.features.auth.dto.RefreshTokenRequest;

public interface AuthService {

    AuthResponse login(AuthRequest request);

    AuthResponse refreshToken(RefreshTokenRequest request);
}
