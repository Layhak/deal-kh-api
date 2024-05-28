package co.istad.dealkh.converter;

import co.istad.dealkh.domain.json.Image;
import jakarta.persistence.Converter;

@Converter
public class ImageListConverter extends JsonListConverter<Image> {
    public ImageListConverter() {
        super(Image.class);
    }
}
