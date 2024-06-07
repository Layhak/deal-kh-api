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


@Getter
@Setter
@RequiredArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, UsernamePasswordAuthenticationToken> {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(JwtToUserConverter.class);

    @Override
    public UsernamePasswordAuthenticationToken convert(Jwt source) {
        logger.info("JwtAuthenticationToken converter" + source.getSubject());
        User user = userRepository.findByEmail(source.getSubject())
                .orElseThrow(() -> new BadCredentialsException("Invalid Token!!! "));
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);

        return new UsernamePasswordAuthenticationToken(
                customUserDetails,
                "",
                customUserDetails.getAuthorities()
        );
    }

}
