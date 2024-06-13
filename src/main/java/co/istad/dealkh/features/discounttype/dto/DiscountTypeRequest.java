package co.istad.dealkh.features.discounttype.dto;

import co.istad.dealkh.domain.Discount;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.util.List;

public record DiscountTypeRequest (
        String name,
        String slug,
        List<String> discounts
) {
}
