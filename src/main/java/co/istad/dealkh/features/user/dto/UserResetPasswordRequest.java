package co.istad.dealkh.features.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserResetPasswordRequest(


        @NotBlank(message = "New password is required")
        String newPassword,

        @NotBlank(message = "New password confirmation is required")
        String newPasswordConfirmation
) {
}
