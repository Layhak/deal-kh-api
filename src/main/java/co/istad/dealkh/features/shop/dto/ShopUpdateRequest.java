package co.istad.dealkh.features.shop.dto;

import co.istad.dealkh.validator.phonenumber.ValidPhoneNumber;
import co.istad.dealkh.validator.slug.ValidSlug;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;
import java.util.List;

public record ShopUpdateRequest (

        String name,

        @ValidSlug(message = "Slug must be properly formatted and can contain lowercase letters, numbers, and single dashes")
        String slug,

        String address,
        String description,

        @ValidPhoneNumber(message = "Phone number must be 10 digits long")
        String phoneNumber,

        String email,
        LocalTime openAt,
        LocalTime closeAt,
        Long shopTypeId,
        List<Long> userIds,
        String location
){
}
