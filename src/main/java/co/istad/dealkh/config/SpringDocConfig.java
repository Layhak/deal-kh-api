package co.istad.dealkh.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SpringDocConfig is a configuration class that sets up the OpenAPI documentation
 * using SpringDoc. It defines a bean for grouping the public API endpoints.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Configuration} - Indicates that the class can be used by the Spring IoC container as a source of bean definitions.</li>
 * <li>{@link Bean} - Indicates that a method produces a bean to be managed by the Spring container.</li>
 * </ul>
 * </p>
 */
//@Configuration
public class SpringDocConfig {

    /**
     * Defines a GroupedOpenApi bean that groups the public API endpoints.
     *
     * <p>The public API group includes all endpoints that match the path pattern "/api/**".</p>
     *
     * @return a {@link GroupedOpenApi} instance for the public API group
     */
//    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("private")
                .pathsToMatch("/api/**")
                .build();
    }
}