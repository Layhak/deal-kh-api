package co.istad.dealkh.features.order.dto;

import java.util.List;

public record OrderRequest(
        long userId,
        List<Long> products
) {
}
