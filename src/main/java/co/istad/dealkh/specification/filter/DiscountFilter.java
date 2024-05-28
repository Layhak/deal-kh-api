package co.istad.dealkh.specification.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DiscountFilter {
    private String name;
    private double discountPercentage;
}
