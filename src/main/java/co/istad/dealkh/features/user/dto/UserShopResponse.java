package co.istad.dealkh.features.user.dto;

public record UserShopResponse(
        String name,
        String slug,
        String description,
        String address,
        String phoneNumber
) {
}
