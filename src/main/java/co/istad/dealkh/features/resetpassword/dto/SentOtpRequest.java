package co.istad.dealkh.features.resetpassword.dto;

import jakarta.validation.constraints.NotBlank;

public record SentOtpRequest(
        @NotBlank(message = "Email is required")
        String email
) {
}
