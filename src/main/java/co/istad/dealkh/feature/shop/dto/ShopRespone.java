package co.istad.dealkh.feature.shop.dto;

import java.util.List;

public record ShopRespone(
        String name,
        String description,
        String address,
        String phoneNumber,
        String email,
        List<String> images

) {
}
