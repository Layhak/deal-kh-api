package co.istad.dealkh.converter;

import co.istad.dealkh.domain.json.SocialMedia;
import jakarta.persistence.Converter;

@Converter
public class SocialListConverter extends JsonListConverter<SocialMedia> {
    public SocialListConverter() {
        super(SocialMedia.class);
    }
}
