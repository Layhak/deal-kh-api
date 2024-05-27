package co.istad.dealkh.feature.shoptype;

import co.istad.dealkh.entity.Shop;
import co.istad.dealkh.entity.ShopType;
import co.istad.dealkh.feature.shoptype.dto.ShopTypeRequest;
import co.istad.dealkh.feature.shoptype.dto.ShopTypeResponse;

import java.util.List;

public interface ShopTypeService {
    List<ShopTypeResponse> getAllShopTypes();
    ShopTypeResponse createShopType(ShopTypeRequest shopTypeRequest);
    ShopTypeResponse updateShopType(Long id, ShopTypeRequest shopTypeRequest);
    void deleteShopType(Long id);
    ShopTypeResponse getShopTypeById(Long id);
    ShopTypeResponse getShopTypeByName(String name);
}
