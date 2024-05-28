package co.istad.dealkh.features.shoptype;

import co.istad.dealkh.features.shoptype.dto.ShopTypeRequest;
import co.istad.dealkh.features.shoptype.dto.ShopTypeResponse;

import java.util.List;

public interface ShopTypeService {
    List<ShopTypeResponse> getAllShopTypes();

    ShopTypeResponse createShopType(ShopTypeRequest shopTypeRequest);

    ShopTypeResponse updateShopType(Long id, ShopTypeRequest shopTypeRequest);

    void deleteShopType(Long id);

    ShopTypeResponse getShopTypeById(Long id);

    ShopTypeResponse getShopTypeByName(String name);
}
