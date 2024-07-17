package co.istad.dealkh.specification.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilter {
    private String name;
    private double discountValue;
    private String discountType;
    private String categoryName;
    private String categorySlug;
    private String shop;
    private double ratingAvg;
    private String discountTypeSlug;
}
