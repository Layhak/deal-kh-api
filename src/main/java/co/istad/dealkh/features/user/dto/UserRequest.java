package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.validator.dateofbirth.ValidDOB;
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
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")

        String password,

        @NotBlank(message = "Gender is required")
        @Size(max = 6)
        String gender,

        @NotBlank(message = "Phone number is required")
        @Size(max = 20, message = "Phone number must less than 20 characters")
        String phoneNumber,

        @ValidDOB
        String dob,

        List<Image> images,

        String location,

        Set<String> roles
//        List<UserShopResponse> shops
) {
}
