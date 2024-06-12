package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.validator.email.ValidEmail;
import co.istad.dealkh.validator.localDate.ValidLocalDate;
import co.istad.dealkh.validator.password.ValidPassword;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String username,

        @NotBlank(message = "Email is required")
        @ValidEmail
        String email,

        @NotBlank(message = "Password is required")
        @ValidPassword(message = "Password is not strong enough! It must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 to 20 characters long.")
        String password,

        @NotBlank(message = "Confirmed password is required")
        String confirmedPassword,

        @NotBlank
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must less than 20 characters")
        String phoneNumber,

        @NotNull(message = "Date of birth is required")
        @ValidLocalDate(message = "Please provide a valid date")
        String dob,

        @NotBlank
        String location
//        List<UserShopResponse> shops
) {
}
