package co.istad.dealkh.converter;

import co.istad.dealkh.domain.json.Image;
import jakarta.persistence.Converter;

/**
 * ImageListConverter is a JPA attribute converter that converts a list of {@link Image} objects to a JSON string
 * and vice versa. It extends the {@link JsonListConverter} class, specifying {@link Image} as the type parameter.
 *
 * <p>This class uses the {@link Converter} annotation to indicate that it is a JPA converter.</p>
 */
@Converter
public class ImageListConverter extends JsonListConverter<Image> {

    /**
     * Constructs a new ImageListConverter, specifying {@link Image} as the type parameter for the superclass.
     */
    public ImageListConverter() {
        super(Image.class);
    }
}
