package co.istad.dealkh.validator.localDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateValidator implements ConstraintValidator<ValidLocalDate, String> {

    private String pattern;

    @Override
    public void initialize(ValidLocalDate constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null || date.isEmpty()) {
            return false; // Consider empty date as invalid
        }
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern(this.pattern));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
