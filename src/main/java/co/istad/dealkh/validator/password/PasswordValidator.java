package co.istad.dealkh.validator.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Pattern;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +           // At least one digit
                    "(?=.*[a-z])" +            // At least one lowercase letter
                    "(?=.*[A-Z])" +            // At least one uppercase letter
                    "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])" +  // At least one special character
                    ".{8,20}$";                // Length between 8 and 20 characters

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    @Override
    public void initialize(ValidPassword constraintAnnotation) {
        // Initialization code if needed
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null || password.isEmpty()) {
            return false; // Consider empty password as invalid
        }
        return pattern.matcher(password).matches();
    }
}
