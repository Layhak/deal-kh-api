package co.istad.dealkh.features.shop.dto;


import co.istad.dealkh.domain.json.SocialMedia;
import co.istad.dealkh.features.image.dto.ImageResponse;
import co.istad.dealkh.validator.email.ValidEmail;
import co.istad.dealkh.validator.localtime.ValidLocalTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
public record ShopCreateRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "address is required")
        String address,

        String description,

        @NotBlank(message = "Phone number is required")
        @Size(min = 5, max = 20, message = "Phone number must be between 5 and 20 characters")
        String phoneNumber,

        @ValidEmail
        String email,

        @ValidLocalTime(message = "Please provide a valid opening time in the format Example:08:00")
        String openAt,

        @ValidLocalTime(message = "Please provide a valid closing time in the format Example:16:00")
        String closeAt,

        @NotNull(message = "Shop type id is required")
        String shopType,


        @NotNull(message = "Location is required")
        String location,

        List<ImageResponse> images,
        List<SocialMedia> socialMedias
) {
}
