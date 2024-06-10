package co.istad.dealkh.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebMvcConfiguration is a configuration class that customizes the Spring MVC configuration.
 * It defines resource handlers for serving static files.
 *
 * <p>This class uses the following annotations:
 * <ul>
 * <li>{@link Configuration} - Indicates that the class can be used by the Spring IoC container as a source of bean definitions.</li>
 * </ul>
 * </p>
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Value("${file.storage-dir}")
    String fileStorageLocation;

    @Value("${file.client-dir}")
    String clientLocation;

    /**
     * Configures resource handlers for serving static files.
     *
     * <p>This method maps a URL path to a file system location for serving static files.
     * The path and location are configured using properties defined in the application configuration.</p>
     *
     * @param registry the {@link ResourceHandlerRegistry} to customize
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(clientLocation)
                .addResourceLocations("file:" + fileStorageLocation);
    }
}
