package co.istad.dealkh.features.shop.dto;


import java.sql.Time;
import java.util.List;

public record ShopRequest(
        String name,
        String address,
        String description,
        String phoneNumber,
        String email,
        Time openAt,
        Time closeAt,
        Long shopTypeId,
        List<Long> userIds,
        String location
) {
}
