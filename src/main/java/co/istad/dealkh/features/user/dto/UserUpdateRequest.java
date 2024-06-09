package co.istad.dealkh.features.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record UserUpdateRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String username,

        @NotBlank
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must less than 20 characters")
        String phoneNumber,

        @NotNull
        LocalDate dob,

        @NotBlank
        String location,

        List<Long> shopId
) {
}
