package co.istad.dealkh.features.user.dto;

public record UserResetPasswordRequest(
        String newPassword,
        String newPasswordConfirmation
) {
}
