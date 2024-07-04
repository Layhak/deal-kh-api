package co.istad.dealkh.features.user.dto;

public record SellerResponse(
        String profile,
        String name,
        String email,
        String gender,
        String phoneNumber,
        String dob,
        String createdAt
) {
}
