package co.istad.dealkh.features.shop.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.sql.Time;
import java.time.LocalTime;
import java.util.List;

/**
 * ShopRequest is a request object for creating a new shop.
 * It contains the name, address, description, phone number, email, open at, close at, shop type ID, user IDs, location.
 *
 * @param name
 * @param address
 * @param description
 * @param phoneNumber
 * @param email
 * @param openAt
 * @param closeAt
 * @param shopTypeId
 * @param userIds
 * @param location
 */
public record ShopRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "address is required")
        String address,

        String description,

        @NotBlank(message = "Phone number is required")
        String phoneNumber,

        String email,

        LocalTime openAt,
        LocalTime closeAt,

        @NotNull(message = "Shop type id is required")
        Long shopTypeId,

        @NotNull(message = "User id is required")
        List<Long> userIds,

        @NotNull(message = "Location is required")
        String location
) {
}
