package co.istad.dealkh.features.shops.dto;

import co.istad.dealkh.entity.json.Image;

import java.time.LocalDateTime;
import java.util.List;

public record ShopRequest(
        String name,
        float latitude, // Separate field for latitude
        float longitude, // Separate field for longitude
        String description,
        String phoneNumber,
        String email,
        LocalDateTime openAt,
        LocalDateTime closeAt,
        Integer shopTypeId,
        List<Image> images,
        List<Long> userIds,
        String location
) {
}
