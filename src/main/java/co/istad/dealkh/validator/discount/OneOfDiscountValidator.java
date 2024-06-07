package co.istad.dealkh.validator.discount;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.IntStream;

public class OneOfDiscountValidator implements ConstraintValidator<OneOfDiscount, Integer> {
    Integer[] arrayOfValues;

    @Override
    public void initialize(OneOfDiscount constraintAnnotation) {
        int[] values = constraintAnnotation.Values();
        this.arrayOfValues = IntStream.of(values).boxed().toArray(Integer[]::new);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return Arrays.asList(this.arrayOfValues).contains(value);
    }
}
