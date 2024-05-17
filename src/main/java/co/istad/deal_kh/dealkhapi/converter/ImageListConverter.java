package co.istad.deal_kh.dealkhapi.converter;

import co.istad.deal_kh.dealkhapi.domain.json.Image;
import jakarta.persistence.Converter;

@Converter
public class ImageListConverter extends JsonListConverter<Image> {
    public ImageListConverter() {
        super(Image.class);
    }
}
