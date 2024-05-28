package co.istad.dealkh.features.shoptype;

import co.istad.dealkh.features.shoptype.dto.ShopTypeCreateRequest;
import co.istad.dealkh.features.shoptype.dto.ShopTypeResponse;
import co.istad.dealkh.features.shoptype.dto.ShopTypeUpdateRequest;
import co.istad.dealkh.paging.PageResponse;

import java.util.Map;
import java.util.Optional;

public interface ShopTypeService {

    ShopTypeResponse createShopType(ShopTypeCreateRequest shopTypeCreateRequest);

    Optional<ShopTypeResponse> getShopTypeByName(String name);

    PageResponse<ShopTypeResponse> filterShopTypes(Map<String, String> params);

    ShopTypeResponse updateShopTypeByName(String name, ShopTypeUpdateRequest shopTypeUpdateRequest);

    void deleteShopTypeByName(String name);
}
