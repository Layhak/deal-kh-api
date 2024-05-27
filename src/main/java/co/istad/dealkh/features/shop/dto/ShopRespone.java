package co.istad.dealkh.features.shop.dto;

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
