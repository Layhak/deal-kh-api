package co.istad.dealkh.features.shop.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.util.List;

public record ShopRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "address is required")
        String address,

        String description,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        @NotBlank(message = "Email is required")
        String email,

        Time openAt,
        Time closeAt,

        @NotNull(message = "Shop type id is required")
        Long shopTypeId,

        @NotNull(message = "User id is required")
        List<Long> userIds,

        @NotNull(message = "Location is required")
        String location
) {
}
