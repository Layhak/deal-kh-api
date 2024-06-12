package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.validator.dateofbirth.ValidDOB;
import co.istad.dealkh.validator.email.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

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
        String password,

        @NotBlank(message = "Confirmed password is required")
        String confirmedPassword,

        @NotBlank
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must less than 20 characters")
        String phoneNumber,

        @NotNull
        @ValidDOB
        LocalDate dob,

        @NotBlank
        String location
//        List<UserShopResponse> shops
) {
}
