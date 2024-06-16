package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;

public interface WishListService {

    WishListResponse addWishList(String username, WishListRequest wishListRequest);

    PageResponse<WishListResponse> getAllWishList(Map<String, String> params);

    void deleteWishList(String uuid);


    WishListResponse grantWishListByUuid(String uuid);

    WishListResponse denyWishListByUuid(String uuid);

    WishListResponse getWishListByUuid(String uuid);

    WishListResponse getWishListByUsername(String username);
}
