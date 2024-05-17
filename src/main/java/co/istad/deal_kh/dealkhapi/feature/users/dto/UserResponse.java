package co.istad.deal_kh.dealkhapi.feature.users.dto;

import co.istad.deal_kh.dealkhapi.domain.Role;
import co.istad.deal_kh.dealkhapi.domain.Shop;
import co.istad.deal_kh.dealkhapi.domain.SocialMedia;

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
        List<String> socialMedias,
        String role,
        List<String> shops
) {
}
