package co.istad.dealkh.config;

import co.istad.dealkh.security.CustomAuthenticationProvider;
import co.istad.dealkh.security.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * AuthProviderConfig is a configuration class that defines beans related to authentication and password encoding.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Configuration} - Indicates that the class can be used by the Spring IoC container as a source of bean definitions.</li>
 * <li>{@link Bean} - Indicates that a method produces a bean to be managed by the Spring container.</li>
 * </ul>
 * </p>
 */
@Configuration
public class AuthProviderConfig {

    /**
     * Defines a {@link PasswordEncoder} bean that uses the {@link BCryptPasswordEncoder} implementation.
     *
     * <p>{@link BCryptPasswordEncoder} is a strong hashing function to encrypt passwords.</p>
     *
     * @return a {@link BCryptPasswordEncoder} instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Defines a {@link CustomAuthenticationProvider} bean that uses a custom implementation of
     * authentication logic.
     *
     * <p>This bean is configured with {@link UserDetailsServiceImpl} to load user-specific data
     * and {@link PasswordEncoder} to perform password encoding and matching.</p>
     *
     * @param userDetailsService the user details service implementation used to load user-specific data
     * @param passwordEncoder    the password encoder used to encode and match passwords
     * @return a {@link CustomAuthenticationProvider} instance
     */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider(UserDetailsServiceImpl userDetailsService, PasswordEncoder passwordEncoder) {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder);
    }
}
