package co.istad.dealkh.features.shop;

import co.istad.dealkh.features.shop.dto.ShopCreateRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;
import co.istad.dealkh.features.shop.dto.ShopUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.List;

public interface ShopService {
    PageResponse<ShopResponse> getAllShop(int page, int size, String field, String order);

    ShopResponse getShopById(String slug);

    ShopResponse createShop(ShopCreateRequest shopRequest);

    ShopResponse updateShop(String slug, ShopUpdateRequest shopRequest);

    void deleteShop(String slug);

    ShopResponse disableShop(String slug);

    ShopResponse enableShop(String slug);

    List<ShopResponse> getShopByUsername
            (String username);

    List<ShopResponse> getShopByShopType(String shopType);

    List<ShopResponse> getNearbyShop(double latitute, double longtitute);

    List<ShopResponse> getShopByName(String name);

}
