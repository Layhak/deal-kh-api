package co.istad.dealkh.features.grantedwishlist.dto;

import co.istad.dealkh.features.wishlist.dto.WishListResponse;

import java.time.LocalDate;

public record GrantedWishListResponse(
        Long id,
        LocalDate grantedDate,
        Double discountPercentage,
        WishListResponse wishList
) {
}
