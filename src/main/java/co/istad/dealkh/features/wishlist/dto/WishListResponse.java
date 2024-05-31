package co.istad.dealkh.features.wishlist.dto;

public record WishListResponse(

        Long id,
        Long discountTypeId,
        String productName,
        Long userId,
        String discountPercentage,
        boolean isGranted
) {
}
