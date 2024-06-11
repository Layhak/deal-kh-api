package co.istad.dealkh.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserRoleRequest(

        @NotBlank(message = "Role is required")
        String role
) {
}
