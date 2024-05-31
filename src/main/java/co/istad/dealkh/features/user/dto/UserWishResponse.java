package co.istad.dealkh.features.user.dto;

import java.time.LocalDate;

public record UserWishResponse(
        String username,
        String email,
        LocalDate dob,
        String phoneNumber,
        String location
) {
}
