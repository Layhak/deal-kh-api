package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.validator.dateofbirth.ValidDob;
import co.istad.dealkh.validator.password.ValidPassword;
import co.istad.dealkh.validator.phonenumber.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

public record UserRequest(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        @ValidPassword(message = "Password is not strong enough! It must contain at least one digit, one lowercase letter, one uppercase letter, one special character, and be between 8 to 20 characters long.")
        String password,

        @NotBlank(message = "Gender is required")
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @ValidPhoneNumber(message = "Phone number must be 9 or 10 digits long")
        String phoneNumber,

        @ValidDob
        String dob,

        List<Image> images,

        String location,

        Set<String> roles
//        List<UserShopResponse> shops
) {
}
