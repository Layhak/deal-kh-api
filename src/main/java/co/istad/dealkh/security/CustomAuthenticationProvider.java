package co.istad.dealkh.security;

import co.istad.dealkh.exception.CustomAuthException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * CustomAuthenticationProvider is an implementation of {@link AuthenticationProvider} that provides custom authentication logic.
 * It uses a custom user details service and password encoder to authenticate users based on email and password.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Component} - Indicates that this class is a Spring component and a candidate for component scanning and dependency injection.</li>
 * </ul>
 * </p>
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsServiceImpl userDetailsService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructs a new CustomAuthenticationProvider with the specified user details service and password encoder.
     *
     * @param userDetailsService the user details service to use for loading user details
     * @param passwordEncoder    the password encoder to use for validating passwords
     */
    public CustomAuthenticationProvider(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Authenticates a user based on the provided email and password.
     *
     * @param authentication the authentication request object containing the user's credentials
     * @return a fully authenticated object including credentials if authentication is successful
     * @throws AuthenticationException if authentication fails
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = authentication.getName();
        String password = (String) authentication.getCredentials();

        List<Map<String, Object>> errors = new ArrayList<>();

        if (email == null || email.isEmpty()) {
            Map<String, Object> error = new TreeMap<>();
            error.put("field", "email");
            error.put("reason", "Email is required");
            errors.add(error);
        } else if (!isValidEmail(email)) {
            Map<String, Object> error = new TreeMap<>();
            error.put("field", "email");
            error.put("reason", "Email is not valid");
            errors.add(error);
        }

        if (password == null || password.isEmpty()) {
            Map<String, Object> error = new TreeMap<>();
            error.put("field", "password");
            error.put("reason", "Password is required");
            errors.add(error);
        } else if (!isValidPassword(password)) {
            Map<String, Object> error = new TreeMap<>();
            error.put("field", "password");
            error.put("reason", "Password is not valid");
            errors.add(error);
        }

        if (!errors.isEmpty()) {
            throw new CustomAuthException(HttpStatus.BAD_REQUEST, errors);
        }

        UserDetails userDetails = userDetailsService.loadUserByEmail(email);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            Map<String, Object> error = new TreeMap<>();
            error.put("field", "password");
            error.put("reason", "Password is not correct");
            errors.add(error);
            throw new CustomAuthException(HttpStatus.BAD_REQUEST, errors);
        }

        return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
    }

    private boolean isValidEmail(String email) {
        //return Email with the valid format follow this pattern: ^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length() <= 20;
    }

    /**
     * Indicates whether this {@link AuthenticationProvider} supports the indicated {@link Authentication} object.
     *
     * @param authentication the class of the authentication object
     * @return true if the authentication object is supported, false otherwise
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
