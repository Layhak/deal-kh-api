package co.istad.dealkh.features.user.dto;

public record UserShopResponse(
        String name,
        String description,
        String address,
        String phoneNumber,
        String email
) {
}
