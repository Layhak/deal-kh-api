package co.istad.dealkh.validator.dateofbirth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DOBValidator implements ConstraintValidator<ValidDOB, LocalDate> {

    @Override
    public void initialize(ValidDOB constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
        if (dob == null) {
            return true; // Let @NotNull handle null cases
        }
        return dob.isBefore(LocalDate.now());
    }
}
