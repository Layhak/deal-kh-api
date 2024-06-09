package co.istad.dealkh.features.order.dto;

import java.util.List;

/**
 * OrderRequest is a request object for creating an order.
 * It contains the user ID and the list of product IDs.
 *
 * @param userId
 * @param products
 */
public record OrderRequest(
        long userId,
        List<Long> products
) {
}
