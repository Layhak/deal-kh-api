package co.istad.dealkh.validator.discount;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.math.BigDecimal;

public class DiscountRangeValidator implements ConstraintValidator<DiscountRange, BigDecimal> {
    private int min;
    private int max;

    @Override
    public void initialize(DiscountRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(BigDecimal value, ConstraintValidatorContext context) {
        if (value == null) {
            // Null is not valid
            return false;
        }
        return value.compareTo(BigDecimal.valueOf(min)) >= 0 && value.compareTo(BigDecimal.valueOf(max)) <= 0;
    }
}
