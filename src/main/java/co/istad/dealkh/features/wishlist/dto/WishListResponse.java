package co.istad.dealkh.features.wishlist.dto;

public record WishListResponse(
        String uuid,
        String profile,
        String productName,
        String username,
        Double discountPercentage,
        String description,
        String isGranted
) {
}
