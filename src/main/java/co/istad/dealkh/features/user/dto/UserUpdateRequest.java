package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.validator.phonenumber.ValidPhoneNumber;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record UserUpdateRequest(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Gender is required")
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @ValidPhoneNumber(message = "Phone number must be 10 digits long")
        String phoneNumber,

        @NotNull(message = "Date of birth is required")
        LocalDate dob,

        String location

//        List<String> shopSlug
) {
}
