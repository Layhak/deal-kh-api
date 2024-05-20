package co.istad.dealkh.feature.users.dto;

import co.istad.dealkh.domain.json.SocialMedia;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
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

        String profileImage,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must less than 20 characters")
        String phoneNumber,

        LocalDate dob,
        String location,
        List<SocialMedia> socialMedias,
        String role
//        List<UserShopResponse> shops
) {
}
