package co.istad.dealkh.features.auth.dto;

import lombok.Builder;

@Builder
public record RefreshTokenRequest(
        String refreshToken
) {
}
