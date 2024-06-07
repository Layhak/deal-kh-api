package co.istad.dealkh.validator.rating;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.DoubleStream;

public class OneOfRatingValidator implements ConstraintValidator<OneOfRating, Double> {
    Double[] arrayOfValues;

    @Override
    public void initialize(OneOfRating constraintAnnotation) {
        double[] values = constraintAnnotation.Values();
        this.arrayOfValues = DoubleStream.of(values).boxed().toArray(Double[]::new);
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return Arrays.asList(this.arrayOfValues).contains(value);
    }
}
