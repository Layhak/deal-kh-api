package co.istad.dealkh.features.shop;

import co.istad.dealkh.domain.Shop;
import co.istad.dealkh.domain.enumType.ShopVerify;
import co.istad.dealkh.features.shop.dto.*;
import co.istad.dealkh.features.user.dto.UserCoverRequest;
import co.istad.dealkh.features.user.dto.UserCoverResponse;
import co.istad.dealkh.features.user.dto.UserProfileRequest;
import co.istad.dealkh.features.user.dto.UserProfileResponse;
import co.istad.dealkh.paging.PageResponse;

import java.util.List;

public interface ShopService {
    PageResponse<ShopResponse> getAllShop(int page, int size, String field, String order);

    PageResponse<ShopResponse> getAllOwnerShop(int page, int size, String field, String order, String username);

    ShopResponse getOwnerShopBySlug(String slug, String username);

    ShopResponse getShopBySlug(String slug);

    ShopResponse createShop(ShopCreateRequest shopRequest, List<String> usernames);

    ShopResponse updateShop(String slug, ShopUpdateRequest shopRequest, String username);

    void deleteShop(String slug, String username);

    ShopResponse disableShop(String slug, String username);

    ShopResponse enableShop(String slug, String username);

    List<ShopResponse> getShopByShopType(String shopType);

    List<ShopResponse> getNearbyShop(double latitute, double longtitute);

    ShopResponse addOwnerToShop(String slug, String username, String owner);

    ShopResponse removeOwnerFromShop(String slug, String username, String owner);


    ShopCoverResponse getAllShopCover(String slug);

    void deleteShopCover(String username, String slug, ShopCoverRequest shopCoverRequest);

    ShopCoverResponse uploadShopCover(String username, String slug, ShopCoverRequest shopCoverRequest);

    ShopProfileResponse getShopProfile(String slug);

    void deleteShopProfile(String username, String slug, String profile);

    ShopProfileResponse uploadShopProfile(String username, String slug, ShopProfileRequest shopProfileRequest);

    PageResponse<ShopResponse> getAllShopRequest(int page, int size, String field, String order);

    void verifyShop(String slug, String username, Boolean verified);
}
