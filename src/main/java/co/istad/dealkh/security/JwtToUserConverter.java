package co.istad.dealkh.security;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

/**
 * JwtToUserConverter is a converter that transforms a JWT into a {@link UsernamePasswordAuthenticationToken}.
 * It retrieves the user details from the database using the email present in the JWT.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Component} - Indicates that this class is a Spring component and a candidate for component scanning and dependency injection.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link Getter} - Generates getters for all fields.</li>
 * <li>{@link Setter} - Generates setters for all fields.</li>
 * </ul>
 * </p>
 */
@Getter
@Setter
@RequiredArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(JwtToUserConverter.class);

    /**
     * Converts the given JWT into a {@link UsernamePasswordAuthenticationToken}.
     *
     * @param source the JWT to convert
     * @return the {@link UsernamePasswordAuthenticationToken} containing the authenticated user details
     * @throws BadCredentialsException if the user cannot be found or the token is invalid
     */
    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        logger.info("JwtAuthenticationToken converter" + source.getSubject());
        User user = userRepository.findByEmail(source.getSubject())
                .orElseThrow(() -> new BadCredentialsException("Invalid Token!!! "));
        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        return new UsernamePasswordAuthenticationToken(
                customUserDetails,
                "",
                customUserDetails.getAuthorities()
        );
    }
}
