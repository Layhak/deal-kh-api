package co.istad.dealkh.features.shop.dto;


import co.istad.dealkh.validator.email.ValidEmail;
import co.istad.dealkh.validator.name.ValidName;
import co.istad.dealkh.validator.phonenumber.ValidPhoneNumber;
import co.istad.dealkh.validator.slug.ValidSlug;
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
        @ValidName(message = "Name must be properly formatted and can contain letters, numbers, single spaces, and single dashes")
        String name,


        @ValidSlug(message = "Slug must be properly formatted and can contain lowercase letters, numbers, and single dashes")
        String slug,

        @NotNull(message = "address is required")
        String address,

        String description,

        @NotBlank(message = "Phone number is required")
        @ValidPhoneNumber(message = "Phone number must be 10 digits long")
        String phoneNumber,

//        @ValidEmail(message = "Email must be properly formatted")
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
