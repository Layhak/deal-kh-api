package co.istad.dealkh.features.order.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * OrderResponse is a response object for an order.
 * It contains the order ID, the list of products, and the date.
 *
 * @param id
 * @param products
 * @param date
 */
public record OrderResponse(
        String uuid,
        String username,
        List<String> products,
        LocalDateTime date

) {
}
