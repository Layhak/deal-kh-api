package co.istad.dealkh.features.wishlist;

import co.istad.dealkh.features.wishlist.dto.WishListRequest;
import co.istad.dealkh.features.wishlist.dto.WishListResponse;
import co.istad.dealkh.features.wishlist.dto.WishListUpdate;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;

public interface WishListService {

    WishListResponse wishList(WishListRequest wishListRequest);

    PageResponse<WishListResponse> getAllWishList(Map<String, String> params);

    WishListResponse updateWishListById(Long id, WishListUpdate wishListUpdate);

    void deleteWishList(Long id);

}
