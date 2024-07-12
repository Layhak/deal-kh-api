package co.istad.dealkh.features.shop.dto;


import co.istad.dealkh.domain.json.SocialMedia;
import co.istad.dealkh.features.image.dto.ImageRequest;
import co.istad.dealkh.features.image.dto.ImageResponse;
import co.istad.dealkh.validator.email.ValidEmail;
import co.istad.dealkh.validator.localtime.ValidLocalTime;
import co.istad.dealkh.validator.name.ValidName;
import co.istad.dealkh.validator.phonenumber.ValidPhoneNumber;
import co.istad.dealkh.validator.slug.ValidSlug;
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
 * @param location
 */
public record ShopCreateRequest(

        @NotBlank(message = "Name is required")
        @ValidName(message = "Name must be properly formatted and can contain letters, numbers, single spaces, and single dashes")
        @Size(max = 100, message = "Name must be less than 100 characters")
        String name,

        @NotNull(message = "address is required")
        String address,

        @Size(max = 500, message = "Description must be less than 500 characters")
        String description,

        @ValidSlug(message = "Slug must be properly formatted and can contain lowercase letters, numbers, and single dashes")
        String slug,

        @NotBlank(message = "Phone number is required")
        @ValidPhoneNumber(message = "Phone number must be 9 or 10 digits long")
        String phoneNumber,

        @Size(max = 100, message = "Email must be less than 100 characters")
        String email,

        @ValidLocalTime(message = "Please provide a valid opening time in the format Example:08:00")
        String openAt,

        @ValidLocalTime(message = "Please provide a valid closing time in the format Example:16:00")
        String closeAt,

        @NotNull(message = "Shop Type Slug  is required")
        String shopType,

        @NotNull(message = "Location is required")
        String location,

        List<SocialMedia> socialMedias,

        String profile
) {
}
