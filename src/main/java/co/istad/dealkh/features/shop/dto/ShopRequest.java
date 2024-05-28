package co.istad.dealkh.features.shop.dto;

import co.istad.dealkh.domain.json.Image;

import java.sql.Struct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ShopRequest(
        String name,
        String address,
        String location,
        String description,
        String phoneNumber,
        String email,
        Long shopTypeId,
        List<Image> Images,
        Long userShopId


) {
}
