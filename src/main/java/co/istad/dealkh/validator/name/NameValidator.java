package co.istad.dealkh.validator.name;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NameValidator implements ConstraintValidator<ValidName, String> {

    @Override
    public void initialize(ValidName constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null || name.isEmpty()) {
            return false;
        }

        // Regular expression to allow names with letters, numbers, single spaces, and single dashes
        // Disallows multiple consecutive dashes or spaces, and invalid symbols
        String validNamePattern = "^[a-zA-Z0-9]+(?:[-\\s][a-zA-Z0-9]+)*$";

        return name.matches(validNamePattern);
    }
}
