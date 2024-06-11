package co.istad.dealkh.validator.discount;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DiscountRangeValidator.class)
public @interface DiscountRange {
    // Minimum value for the discount
    int min() default 0;

    // Maximum value for the discount
    int max() default 100;

    // Error message
    String message() default "The discount value must be between {min} and {max}";

    // Represents group of constraints
    Class<?>[] groups() default {};

    // Represents additional information about annotation
    Class<? extends Payload>[] payload() default {};
}
