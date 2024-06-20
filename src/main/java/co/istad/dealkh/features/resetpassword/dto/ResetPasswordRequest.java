package co.istad.dealkh.features.resetpassword.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;
import org.springframework.web.bind.annotation.RequestParam;

public record ResetPasswordRequest(

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Confirmation code is required")
        Integer confirmationCode,

        @NotBlank(message = "New password is required")
        String newPassword,

        @NotBlank(message = "Confirm password is required")
        String confirmPassword
) {
}
