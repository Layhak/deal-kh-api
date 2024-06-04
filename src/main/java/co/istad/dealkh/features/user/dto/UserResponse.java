package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.domain.Role;
import co.istad.dealkh.features.image.dto.ImageResponse;
import co.istad.dealkh.features.socialMedia.dto.SocialMediaResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String username,
        String email,
        String gender,
        List<ImageResponse> images,
        String phoneNumber,
        LocalDate dob,
        String location,
        Boolean isDisabled,
        LocalDate createdAt,
        LocalDate updatedAt,
        List<SocialMediaResponse> socialMedias,
        Set<String> roles,
        List<UserShopResponse> shops
) {
}
