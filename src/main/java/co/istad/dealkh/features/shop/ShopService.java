package co.istad.dealkh.features.shop;

import co.istad.dealkh.features.shop.dto.ShopRequest;
import co.istad.dealkh.features.shop.dto.ShopResponse;

public interface ShopService {
    ShopResponse createShop(ShopRequest shopRequest);
}
