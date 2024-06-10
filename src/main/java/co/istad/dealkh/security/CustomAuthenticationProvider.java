package co.istad.dealkh.security;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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

        if (email == null || email.isEmpty()) {
            throw new BadCredentialsException("Email cannot be null or empty");
        }

        if (password == null || password.isEmpty()) {
            throw new BadCredentialsException("Password cannot be null or empty");
        }

        UserDetails userDetails = userDetailsService.loadUserByEmail(email);

        if (passwordEncoder.matches(password, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid email or password");
        }
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
