package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;

public interface WishListService {

    WishListResponse addWishList(WishListRequest wishListRequest);

    PageResponse<WishListResponse> getAllWishList(Map<String, String> params);

    void deleteWishList(Long id);


    WishListResponse grantWishList(Long id);

    WishListResponse denyWishList(Long id);
}
