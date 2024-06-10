package co.istad.dealkh.features.order.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequest(

        @NotNull(message = "User id is required")
        long userId,

        @NotNull(message = "Product id is required")
        List<Long> products
) {
}
