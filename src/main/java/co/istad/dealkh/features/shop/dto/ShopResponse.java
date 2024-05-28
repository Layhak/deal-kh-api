package co.istad.dealkh.features.shop.dto;

import co.istad.dealkh.domain.json.Image;

import co.istad.dealkh.features.shoptype.dto.ShopTypeResponse;
import co.istad.dealkh.features.user.dto.UserResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
         LocalDate openAt,
         LocalDate closeAt,
         ShopTypeResponse shopType,
         List<Image> images,
         List<UserResponse> users,
         String location
) {
}
