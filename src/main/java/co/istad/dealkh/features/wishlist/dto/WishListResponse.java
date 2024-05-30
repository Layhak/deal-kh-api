package co.istad.dealkh.features.wishlist.dto;

public record WishListResponse(

        Long id,
        String description,
        boolean isGranted,
        Long userId,
        Long productId
) {
}
