package co.istad.dealkh.feature.users.dto;

public record UserShopResponse(
        String name,
        String description,
        String address,
        String phoneNumber,
        String email
) {
}
