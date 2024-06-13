package co.istad.dealkh.validator.phonenumber;

import co.istad.dealkh.validator.phonenumber.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberValidator implements ConstraintValidator<ValidPhoneNumber, String> {

    @Override
    public void initialize(ValidPhoneNumber constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (phoneNumber == null) {
            return false;
        }

        // Define the phone number pattern
        // This example assumes phone numbers should be 10 digits long
        // Adjust the regex pattern according to your specific requirements
        String phoneNumberPattern = "^[0-9]{10}$";

        return phoneNumber.matches(phoneNumberPattern);
    }
}
