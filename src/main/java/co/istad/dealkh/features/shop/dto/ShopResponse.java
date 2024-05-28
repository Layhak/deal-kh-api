package co.istad.dealkh.features.shop.dto;

import co.istad.dealkh.domain.ShopType;
import co.istad.dealkh.domain.json.Image;

import java.util.List;

public record ShopResponse(
        Long id,
        String name,
        String address,
        String location,
        String description,
        String phoneNumber,
        String email,
        ShopType shopType,
        List<Image> Images,
        List<Long> userShopId
) {
}
