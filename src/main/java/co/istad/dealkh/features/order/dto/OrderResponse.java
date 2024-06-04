package co.istad.dealkh.features.order.dto;

import co.istad.dealkh.features.product.dto.ProductResponse;

import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        List<ProductResponse> products,
        LocalDateTime date

) {
}
