package co.istad.dealkh.features.discount.dto;

import java.time.LocalDate;

public record DiscountUpdateRequest(

        String name,
        String description,
        double discountPercentage,
        LocalDate expiredAt

) {
}
