package co.istad.dealkh.features.grantedwishlist.dto;

import co.istad.dealkh.domain.WishList;

import java.time.LocalDate;

public record GrantedWishListRequest(
        Long discountId,
        Long wishListId
) {
}

