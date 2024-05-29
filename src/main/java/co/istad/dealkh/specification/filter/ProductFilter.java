package co.istad.dealkh.specification.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductFilter {
    private String name;
    private double discountPercentage;
    private String category;
}
