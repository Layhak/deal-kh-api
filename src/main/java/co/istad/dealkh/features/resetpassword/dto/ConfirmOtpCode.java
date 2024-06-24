package co.istad.dealkh.features.resetpassword.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ConfirmOtpCode(

    @NotNull(message = "Opt code is required")
    Integer otp
) {
}
