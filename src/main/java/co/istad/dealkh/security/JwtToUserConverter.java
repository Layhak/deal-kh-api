package co.istad.dealkh.security;

import co.istad.dealkh.domain.User;
import co.istad.dealkh.features.user.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;


@Getter
@Setter
@RequiredArgsConstructor
@Component
public class JwtToUserConverter implements Converter<Jwt, JwtAuthenticationToken> {
    private final UserRepository userRepository;

    @Override
    public JwtAuthenticationToken convert(Jwt source) {
        User user = userRepository.findByEmail(source.getSubject())
                .orElseThrow(() -> new BadCredentialsException("Invalid Token!!! "));
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setUser(user);


        return new JwtAuthenticationToken(
                source,
                customUserDetails.getAuthorities()
        );
    }

}
