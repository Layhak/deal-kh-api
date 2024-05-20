package co.istad.dealkh.feature.users.dto;

import co.istad.dealkh.domain.json.SocialMedia;

import java.time.LocalDate;
import java.util.List;

public record UserResponse(
        Long id,
        String firstName,
        String lastName,
        String email,
        String gender,
        String profileImage,
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
