package co.istad.dealkh.validator.slug;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SlugValidator implements ConstraintValidator<ValidSlug, String> {

    @Override
    public void initialize(ValidSlug constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(String slug, ConstraintValidatorContext context) {
        if (slug == null || slug.isEmpty()) {
            return true;
        }
        // Regular expression to ensure slugs with numbers, lowercase letters, and single dashes
        String validSlugPattern = "^[a-z0-9]+(-[a-z0-9]+)*$";

        return slug.matches(validSlugPattern);
    }
}
