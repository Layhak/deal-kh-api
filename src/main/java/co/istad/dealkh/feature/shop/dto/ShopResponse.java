package co.istad.dealkh.feature.shop.dto;

import co.istad.dealkh.entity.json.Image;

import java.time.LocalDateTime;
import java.util.List;

public record ShopResponse(
         Long id,
         String name,
         float latitude,
         float longitude,
         String description,
         String phoneNumber,
         String email,
         Boolean isDeleted,
         Boolean isDisabled,
         LocalDateTime openAt,
         LocalDateTime closeAt,
         Integer shopTypeId,
         List<Image> images,
         List<Long> userIds, // Add userIds field to accommodate multiple users
         String location
) {
}
