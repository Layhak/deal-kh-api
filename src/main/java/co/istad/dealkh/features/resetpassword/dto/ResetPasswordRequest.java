package co.istad.dealkh.features.resetpassword.dto;

import co.istad.dealkh.validator.password.ValidPassword;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import org.springframework.web.bind.annotation.RequestParam;

public record ResetPasswordRequest(

        @NotNull(message = "Otp is required")
        Integer otp,

        @NotBlank(message = "New password is required")
        @ValidPassword(message = "Password is not strong enough! It must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 to 20 characters long.")
        String newPassword,

        @NotBlank(message = "Confirm password is required")
        @ValidPassword(message = "Password is not strong enough! It must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 to 20 characters long.")
        String confirmPassword
) {
}
