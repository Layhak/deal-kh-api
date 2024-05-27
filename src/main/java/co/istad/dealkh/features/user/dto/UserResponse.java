package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.domain.json.Image;
import co.istad.dealkh.domain.json.SocialMedia;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String gender,
        List<Image> images,
        String phoneNumber,
        LocalDate dob,
        String location,
        Boolean isDisabled,
        LocalDate createdAt,
        LocalDate updatedAt,
        List<SocialMedia> socialMedias,
        String role,
        List<UserShopResponse> shops
) {
}
