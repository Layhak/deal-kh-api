package co.istad.dealkh.features.shop;

import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;

import java.util.List;

public interface ShopService {
    List<ShopResponse> getAllShop();
    ShopResponse getShopById(Long id);

    ShopResponse createShop(ShopRequest shopRequest);

    ShopResponse updateShop(Long id, ShopRequest shopRequest);

    void deleteShop(Long id);

    ShopResponse disableShop(Long id);

    ShopResponse enableShop(Long id);
    List<ShopResponse> getShopByUserId(Long userId);
    List<ShopResponse> getShopByShopType(String shopType);

    List<ShopResponse> getNearbyShop(double latitute, double longtitute);
    List<ShopResponse> getShopByName(String name);

}
