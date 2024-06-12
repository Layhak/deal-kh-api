package co.istad.dealkh.validator.user;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.*[0-9])" +           // At least one digit
                    "(?=.*[a-z])" +            // At least one lowercase letter
                    "(?=.*[A-Z])" +            // At least one uppercase letter
                    "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>])" +  // At least one special character
                    ".{8,20}$";                // Length between 8 and 20 characters

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(String password) {
        return pattern.matcher(password).matches();
    }
}
