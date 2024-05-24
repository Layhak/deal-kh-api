package co.istad.dealkh.converter;

import co.istad.dealkh.entity.json.SocialMedia;
import jakarta.persistence.Converter;

@Converter
public class SocialListConverter extends JsonListConverter<SocialMedia> {
    public SocialListConverter() {
        super(SocialMedia.class);
    }
}
