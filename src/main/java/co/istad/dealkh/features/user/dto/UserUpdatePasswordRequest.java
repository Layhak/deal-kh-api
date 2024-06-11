package co.istad.dealkh.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserUpdatePasswordRequest(

        @NotBlank(message = "Old password is required")
        String oldPassword,

        @NotBlank(message = "New password is required")
        String newPassword,

        @NotBlank(message = "New password confirmation is required")
        String newPasswordConfirmation
) {
}
