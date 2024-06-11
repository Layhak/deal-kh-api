package co.istad.dealkh.validator.discount;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DiscountRangeValidator implements ConstraintValidator<DiscountRange, Integer> {
    private int min;
    private int max;

    @Override
    public void initialize(DiscountRange constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Assuming null values are valid. Adjust as needed.
        }
        return value >= min && value <= max;
    }
}
