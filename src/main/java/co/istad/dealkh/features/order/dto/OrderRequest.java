package co.istad.dealkh.features.order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * OrderRequest is a request object for creating an order.
 * It contains the user ID and the list of product IDs.
 *
 * @param userId
 * @param products
 */
public record OrderRequest(

        @NotNull(message = "User id is required")
        long userId,

        @NotNull(message = "Product id is required")
        List<Long> products
) {
}
