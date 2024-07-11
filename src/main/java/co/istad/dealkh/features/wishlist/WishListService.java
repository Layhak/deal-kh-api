package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.domain.Product;
import co.istad.dealkh.domain.WishList;
import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.paging.PageResponse;

import java.util.List;
import java.util.Map;

public interface WishListService {

    WishListResponse addWishList(String username, WishListRequest wishListRequest);

    PageResponse<WishListResponse> getAllWishList(int page, int size, String field, String order, Map<String, String> params);

    void deleteWishList(String username, String uuid);

    WishListResponse updateWishList(String uuid, String username, WishListRequest wishListRequest);

    WishListResponse grantWishListByUuid(String email, String uuid);

    WishListResponse denyWishListByUuid(String uuid);

    WishListResponse getWishListByUuid(String uuid);

    PageResponse<WishListResponse> getWishListByUsername(int page, int size, String field, String order, Map<String, String> params, String username);

    List<WishListResponse> getWishListByShop(String slug);
}
