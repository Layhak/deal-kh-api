package co.istad.dealkh.validator.dateofbirth;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DobValidator implements ConstraintValidator<ValidDob, String> {

    private String pattern;

    @Override
    public void initialize(ValidDob constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String dateStr, ConstraintValidatorContext context) {
        if (dateStr == null || dateStr.isEmpty()) {
            return false; // Consider empty date as invalid
        }
        try {
            LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(this.pattern));
            LocalDate today = LocalDate.now();
            int age = Period.between(date, today).getYears();
            return date.isBefore(today) && age >= 18 && age <= 100; // Valid if the user is 18+ and <= 100 years old
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
