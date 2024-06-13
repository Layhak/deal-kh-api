package co.istad.dealkh.features.shop.dto;


import co.istad.dealkh.features.image.dto.ImageResponse;

import java.time.LocalTime;
import java.util.List;

/**
 * ShopResponse is a response object for a shop.
 * It contains the ID, name, address, description, phone number, email, is deleted, is disabled, open at, close at, shop type, images, users, location.
 *
 * @param id
 * @param name
 * @param address
 * @param description
 * @param phoneNumber
 * @param email
 * @param isDeleted
 * @param isDisabled
 * @param openAt
 * @param closeAt
 * @param shopType
 * @param images
 * @param users
 * @param location
 */
public record ShopResponse(
//        Long id,
        String name,
        String slug,
        String address,
        String description,
        String phoneNumber,
        String email,
        Boolean isDeleted,
        Boolean isDisabled,
        LocalTime openAt,
        LocalTime closeAt,
        String shopType,
        List<ImageResponse> images,
        List<String> owners,
        String location
) {
}
