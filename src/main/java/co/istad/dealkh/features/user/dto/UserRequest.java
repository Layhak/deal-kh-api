package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.domain.json.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Set;
import java.util.List;

public record UserRequest(
        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        String username,

        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password,

        @NotBlank
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must less than 20 characters")
        String phoneNumber,

        @NotBlank
        LocalDate dob,

        List<Image> images,

        @NotBlank
        String location,
        Set<String> roles
//        List<UserShopResponse> shops
) {
}
