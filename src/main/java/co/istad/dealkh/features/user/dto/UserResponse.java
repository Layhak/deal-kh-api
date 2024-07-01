package co.istad.dealkh.features.user.dto;

import co.istad.dealkh.domain.json.SocialMedia;
import co.istad.dealkh.features.image.dto.ImageResponse;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public record UserResponse(
        String firstName,
        String lastName,
        String username,
        String email,
        String gender,
        String profile,
        List<ImageResponse> covers,
        String phoneNumber,
        LocalDate dob,
        String location,
        Boolean isDisabled,
        LocalDate createdAt,
        LocalDate updatedAt,
        List<SocialMedia> socialMedias,
        Set<String> roles,
        List<UserShopResponse> shops
) {
}
