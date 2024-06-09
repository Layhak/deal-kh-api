package co.istad.dealkh.config;

import co.istad.dealkh.security.CustomAuthenticationProvider;
import co.istad.dealkh.security.JwtToUserConverter;
import co.istad.dealkh.security.KeyUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtToUserConverter jwtToUserConverter;
    private final KeyUtils keyUtils;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authz -> authz

                // auth
                .requestMatchers("/api/v1/auth/**").permitAll()

                // users
                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAnyAuthority("ADMIN", "SUPPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("ADMIN", "SUPPER_ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                .requestMatchers(HttpMethod.PATCH, "/api/v1/users/**").hasAnyAuthority("ADMIN", "SUPPER_ADMIN")

                // discounts
                .requestMatchers(HttpMethod.GET, "/api/v1/discounts/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/discounts/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/discounts/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/discounts/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/discounts/**").hasAuthority("SELLER")

                // products
                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/products/**").hasAuthority("SELLER")

                // shops
                .requestMatchers(HttpMethod.GET, "/api/v1/shops/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/shops/**").hasAuthority("BUYER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/shops/**").hasAnyAuthority("SELLER", "SUPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/shops/**").hasAnyAuthority("SELLER", "ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/shops/**").hasAnyAuthority("SELLER", "ADMIN")

                // wishlists
                .requestMatchers(HttpMethod.GET, "/api/v1/wishlists/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/wishlists/**").hasAnyAuthority("BUYER", "SELLER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/wishlists/**").hasAuthority("BUYER")

                // categories
                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasAuthority("SELLER")

                // product-ratings
                .requestMatchers(HttpMethod.GET, "/api/v1/product-ratings/**").hasAuthority("SELLER")
                .requestMatchers(HttpMethod.POST, "/api/v1/product-ratings/**").hasAuthority("BUYER")

                //shop-types
                .requestMatchers(HttpMethod.GET, "/api/v1/shop-types/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/shop-types/**").hasAnyAuthority("ADMIN", "SUPPER_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/shop-types/**").hasAnyAuthority("ADMIN", "SUPPER_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/shop-types/**").hasAnyAuthority("ADMIN", "SUPPER_ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/shop-types/**").hasAnyAuthority("ADMIN", "SUPPER_ADMIN")

                // product-feedbacks
                .requestMatchers(HttpMethod.GET, "/api/v1/product-feedbacks/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/product-feedbacks/**").hasAuthority("BUYER")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/product-feedbacks/**").hasAnyAuthority("BUYER", "SELLER")
                .requestMatchers(HttpMethod.PATCH, "/api/v1/product-feedbacks/**").hasAuthority("BUYER")

                // images
                .requestMatchers("/api/v1/images/**").permitAll()

                .anyRequest().permitAll()
            )
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(AbstractHttpConfigurer::disable)
            .httpBasic(AbstractHttpConfigurer::disable)
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer ->
                    jwtConfigurer.jwtAuthenticationConverter(jwtToUserConverter)))
            .sessionManagement(session -> session
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(ex -> ex
                    .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                    .accessDeniedHandler(new BearerTokenAccessDeniedHandler()))
            .authenticationProvider(customAuthenticationProvider);
        return http.build();
    }

    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder jwtRefreshTokenEncoder() {
        JWK jwk = new RSAKey.Builder(keyUtils.getRefreshTokenPublicKey())
                .privateKey(keyUtils.getRefreshTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(keyUtils.getRefreshTokenPublicKey())
                .build();
    }

    @Bean
    @Primary
    JwtEncoder jwtAccessTokenEncoder() {
        JWK jwk = new RSAKey.Builder(keyUtils.getAccessTokenPublicKey())
                .privateKey(keyUtils.getAccessTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Primary
    JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(keyUtils.getAccessTokenPublicKey())
                .build();
    }

    @Bean
    JwtAuthenticationProvider accessTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(
                jwtAccessTokenDecoder()
        );
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;
    }

    @Bean
    JwtAuthenticationProvider refreshTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(
                jwtRefreshTokenDecoder()
        );
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;
    }
}
