package co.istad.dealkh.features.shop.dto;


import java.sql.Time;
import java.util.List;

/**
 * ShopRequest is a request object for creating a new shop.
 * It contains the name, address, description, phone number, email, open at, close at, shop type ID, user IDs, location.
 *
 * @param name
 * @param address
 * @param description
 * @param phoneNumber
 * @param email
 * @param openAt
 * @param closeAt
 * @param shopTypeId
 * @param userIds
 * @param location
 */
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
