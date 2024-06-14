package co.istad.dealkh.features.shop;

import co.istad.dealkh.features.shop.dto.ShopCreateRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shop.dto.ShopUpdateRequest;
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

    boolean isShopOwner(String slug, String username);

}
