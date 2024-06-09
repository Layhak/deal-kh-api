package co.istad.dealkh.features.user.dto;

public record UserUpdatePasswordRequest(
        String oldPassword,
        String newPassword,
        String newPasswordConfirmation
) {
}
