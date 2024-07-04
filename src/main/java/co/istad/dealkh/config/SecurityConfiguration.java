package co.istad.dealkh.config;

import co.istad.dealkh.security.*;
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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * SecurityConfiguration is a configuration class that defines beans and configurations for the security setup.
 * It configures JWT authentication, custom authentication providers, and the security filter chain.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Configuration} - Indicates that the class can be used by the Spring IoC container as a source of bean definitions.</li>
 * <li>{@link RequiredArgsConstructor} - Generates a constructor with required arguments (final fields).</li>
 * <li>{@link Bean} - Indicates that a method produces a bean to be managed by the Spring container.</li>
 * <li>{@link Primary} - Indicates that a bean should be given preference when multiple beans of the same type are available.</li>
 * <li>{@link Qualifier} - Used to distinguish beans of the same type.</li>
 * </ul>
 * </p>
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtToUserConverter jwtToUserConverter;
    private final KeyUtils keyUtils;
    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    /**
     * Configures the security filter chain.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the {@link SecurityFilterChain}
     * @throws Exception if an error occurs configuring the security filter chain
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth

                                // auth
                                .requestMatchers("/api/v1/auth/**").permitAll()
                                .requestMatchers("/api/v1/mail/send-email").permitAll()

                                //products scrap
                                .requestMatchers("/api/v1/product-scrape/**").permitAll()

                                // users
                                .requestMatchers( "/api/v1/users/profile").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_SELLER", "ROLE_BUYER")
                                .requestMatchers( "/api/v1/users/cover").hasAnyAuthority("ROLE_SUPER_ADMIN", "ROLE_ADMIN", "ROLE_SELLER", "ROLE_BUYER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/me").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN", "ROLE_SELLER", "ROLE_BUYER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/users/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")

                                // discounts
                                .requestMatchers(HttpMethod.GET, "/api/v1/discounts/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/discounts/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/discounts/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/discounts/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/discounts/**").hasAuthority("ROLE_SELLER")

                                // products
                                .requestMatchers(HttpMethod.GET, "/api/v1/products/shop/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/products/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/products/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/products/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/products/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/products/**").hasAuthority("ROLE_SELLER")

                                // shops endpoints
                                // cover
                                .requestMatchers(HttpMethod.POST, "/api/v1/shops/{slug}/cover").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/shops/{slug}/cover").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/shops/{slug}/cover").permitAll()

                                // profile
                                .requestMatchers(HttpMethod.POST, "/api/v1/shops/{slug}/profile").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/shops/{slug}/profile").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/shops/{slug}/profile").permitAll()

                                .requestMatchers(HttpMethod.GET, "/api/v1/shops/owner").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.GET, "/api/v1/shops/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/shops/**").hasAnyAuthority("ROLE_SELLER", "ROLE_BUYER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/shops/**").hasAnyAuthority("ROLE_SELLER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/shops/**").hasAnyAuthority("ROLE_SELLER", "ROLE_ADMIN", "ROLE_SUPER_ADMIN")

                                // wishlists
                                .requestMatchers(HttpMethod.GET, "/api/v1/wishlists/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/wishlists/**").hasAuthority("ROLE_BUYER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/wishlists/{uuid}/grant").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/wishlists/{uuid}/deny").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/wishlists/**").hasAuthority("ROLE_BUYER")

                                // categories
                                .requestMatchers(HttpMethod.GET, "/api/v1/categories/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/categories/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/categories/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/categories/**").hasAnyAuthority( "ROLE_ADMIN", "ROLE_SUPER_ADMIN")

                                // product-ratings
                                .requestMatchers(HttpMethod.GET, "/api/v1/product-ratings/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/product-ratings/**").hasAuthority("ROLE_BUYER")

                                //shop-types
                                .requestMatchers(HttpMethod.GET, "/api/v1/shop-types/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/shop-types/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/shop-types/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/shop-types/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/shop-types/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_SUPER_ADMIN")

                                // product-feedbacks
                                .requestMatchers(HttpMethod.GET, "/api/v1/product-feedbacks/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/product-feedbacks/**").hasAuthority("ROLE_BUYER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/product-feedbacks/**").hasAnyAuthority("ROLE_BUYER", "ROLE_SELLER")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/product-feedbacks/**").hasAuthority("ROLE_BUYER")

                                //order
                                .requestMatchers(HttpMethod.GET, "/api/v1/orders/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/orders/**").hasAuthority("ROLE_BUYER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/orders/**").hasAuthority("ROLE_BUYER")

                                //coupons
                                .requestMatchers(HttpMethod.GET, "/api/v1/coupons/").permitAll()
                                .requestMatchers(HttpMethod.GET, "/api/v1/coupons/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.GET, "api/v1/coupons/users/claims").hasAuthority("ROLE_BUYER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/coupons/claim/").hasAuthority("ROLE_BUYER")
                                .requestMatchers(HttpMethod.POST, "/api/v1/coupons/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/coupons/**").hasAuthority("ROLE_SELLER")
                                .requestMatchers(HttpMethod.PATCH, "/api/v1/coupons/**").hasAuthority("ROLE_SELLER")

                                // banners
                                .requestMatchers("/api/v1/banners/**").hasAuthority("ROLE_ADMIN")

                                // images
                                .requestMatchers("/api/v1/images/**").permitAll()
                                .requestMatchers("/images/**").permitAll()

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
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler))
                .authenticationProvider(customAuthenticationProvider);
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Creates a JWT encoder for refresh tokens.
     *
     * @return a {@link JwtEncoder} for refresh tokens
     */
    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder jwtRefreshTokenEncoder() {
        JWK jwk = new RSAKey.Builder(keyUtils.getRefreshTokenPublicKey())
                .privateKey(keyUtils.getRefreshTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * Creates a JWT decoder for refresh tokens.
     *
     * @return a {@link JwtDecoder} for refresh tokens
     */
    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    JwtDecoder jwtRefreshTokenDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(keyUtils.getRefreshTokenPublicKey())
                .build();
    }

    /**
     * Creates a primary JWT encoder for access tokens.
     *
     * @return a {@link JwtEncoder} for access tokens
     */
    @Bean
    @Primary
    JwtEncoder jwtAccessTokenEncoder() {
        JWK jwk = new RSAKey.Builder(keyUtils.getAccessTokenPublicKey())
                .privateKey(keyUtils.getAccessTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    /**
     * Creates a primary JWT decoder for access tokens.
     *
     * @return a {@link JwtDecoder} for access tokens
     */
    @Bean
    @Primary
    JwtDecoder jwtAccessTokenDecoder() {
        return NimbusJwtDecoder
                .withPublicKey(keyUtils.getAccessTokenPublicKey())
                .build();
    }

    /**
     * Creates a JWT authentication provider for access tokens.
     *
     * @return a {@link JwtAuthenticationProvider} for access tokens
     */
    @Bean
    JwtAuthenticationProvider accessTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(
                jwtAccessTokenDecoder()
        );
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;
    }

    /**
     * Creates a JWT authentication provider for refresh tokens.
     *
     * @return a {@link JwtAuthenticationProvider} for refresh tokens
     */
    @Bean
    JwtAuthenticationProvider refreshTokenAuthProvider() {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(
                jwtRefreshTokenDecoder()
        );
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;
    }
}
