package co.istad.dealkh.converter;

import co.istad.dealkh.domain.json.SocialMedia;
import jakarta.persistence.Converter;

/**
 * SocialListConverter is a JPA attribute converter that converts a list of {@link SocialMedia} objects to a JSON string
 * and vice versa. It extends the {@link JsonListConverter} class, specifying {@link SocialMedia} as the type parameter.
 *
 * <p>This class uses the {@link Converter} annotation to indicate that it is a JPA converter.</p>
 */
@Converter
public class SocialListConverter extends JsonListConverter<SocialMedia> {

    /**
     * Constructs a new SocialListConverter, specifying {@link SocialMedia} as the type parameter for the superclass.
     */
    public SocialListConverter() {
        super(SocialMedia.class);
    }
}
