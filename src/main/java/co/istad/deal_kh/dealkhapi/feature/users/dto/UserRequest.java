package co.istad.deal_kh.dealkhapi.feature.users.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public record UserRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank
        @Size(max = 6)
        String gender,

        String profileImage,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must less than 20 characters")
        String phoneNumber,

        LocalDate dob,
        String location,
        Boolean isDisabled,
        LocalDate createdAt,
        LocalDate updatedAt,
        List<String> socialMedias,
        String role,
        List<String> shops
) {
}
