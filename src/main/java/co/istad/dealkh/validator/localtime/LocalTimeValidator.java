package co.istad.dealkh.validator.localtime;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalTimeValidator implements ConstraintValidator<ValidLocalTime, String> {

    private String pattern;

    @Override
    public void initialize(ValidLocalTime constraintAnnotation) {
        this.pattern = constraintAnnotation.pattern();
    }

    @Override
    public boolean isValid(String time, ConstraintValidatorContext context) {
        if (time == null || time.isEmpty()) {
            return false; // Consider empty time as invalid
        }
        try {
            LocalTime.parse(time, DateTimeFormatter.ofPattern(this.pattern));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
