package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.validator.password.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public record UserUpdatePasswordRequest(

        @NotBlank(message = "New password is required")
        @ValidPassword
        String newPassword,

        @NotBlank(message = "New password confirmation is required")
        String newPasswordConfirmation
) {
}
