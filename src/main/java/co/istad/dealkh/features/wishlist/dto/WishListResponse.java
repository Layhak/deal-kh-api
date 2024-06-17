package co.istad.dealkh.features.wishlist.dto;

public record WishListResponse(

        String id,
        String discountTypeSlug,
        String productName,
        String username,
        Double discountPercentage,
        String isGranted
) {
}
