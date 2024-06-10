package co.istad.dealkh.features.order.dto;

import co.istad.dealkh.features.product.dto.ProductResponse;

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
        Long id,
        List<ProductResponse> products,
        LocalDateTime date

) {
}
