package co.istad.dealkh.validator.localDate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateValidator implements ConstraintValidator<ValidLocalDate, String> {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private DateTimeFormatter dateFormatter;

    @Override
    public void initialize(ValidLocalDate constraintAnnotation) {
        dateFormatter = DateTimeFormatter.ofPattern(DATE_PATTERN);
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if (date == null || date.isEmpty()) {
            return false; // Consider empty date as invalid
        }
        try {
            LocalDate.parse(date, dateFormatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
