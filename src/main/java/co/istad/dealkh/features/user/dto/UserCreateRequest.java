package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.validator.dateofbirth.ValidDob;
import co.istad.dealkh.validator.email.ValidEmail;
import co.istad.dealkh.validator.password.ValidPassword;
import co.istad.dealkh.validator.phonenumber.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Email is required")
        @ValidEmail(message = "Email must be properly formatted")
        String email,

        @NotBlank(message = "Password is required")
        @ValidPassword(message = "Password is not strong enough! It must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 to 20 characters long.")
        String password,

        @NotBlank(message = "Confirmed password is required")
        String confirmedPassword,

        @NotBlank(message = "Gender is required")
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @ValidPhoneNumber(message = "Phone number must be 9 or 10 digits long")
        String phoneNumber,

        @NotNull(message = "Date of birth is required")
        @ValidDob
        String dob,

        String location
//        List<UserShopResponse> shops
) {
}
