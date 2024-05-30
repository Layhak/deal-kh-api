package co.istad.dealkh.features.shop.dto;

import co.istad.dealkh.domain.json.Image;

import java.sql.Time;
import java.util.List;

public record ShopResponse(
        Long id,
        String name,
        String address,
        String description,
        String phoneNumber,
        String email,
        Boolean isDeleted,
        Boolean isDisabled,
        Time openAt,
        Time closeAt,
        String shopType,
        List<Image> images,
        List<String> users,
        String location
) {
}
