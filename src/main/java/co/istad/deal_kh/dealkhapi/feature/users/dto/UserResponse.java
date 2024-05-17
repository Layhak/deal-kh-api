package co.istad.deal_kh.dealkhapi.feature.users.dto;

import co.istad.deal_kh.dealkhapi.domain.Shop;
import co.istad.deal_kh.dealkhapi.domain.json.SocialMedia;

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
        List<Shop> shops
) {
}
